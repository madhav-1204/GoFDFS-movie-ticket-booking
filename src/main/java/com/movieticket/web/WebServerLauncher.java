package com.movieticket.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.DefaultServlet;

public class WebServerLauncher {

    public static void main(String[] args) throws Exception {
        // Create a new Jetty server on port 3000
        Server server = new Server(3000);

        // Create a context handler to hold our servlets
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Set resource base - use absolute path to webapp directory
        String webappDir = System.getProperty("user.dir") + "/src/main/webapp";
        context.setResourceBase(webappDir);
        System.out.println("Resource base: " + webappDir);

        server.setHandler(context);

        // Register all servlets FIRST
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new RegisterServlet()), "/register");
        context.addServlet(new ServletHolder(new MoviesServlet()), "/movies");
        context.addServlet(new ServletHolder(new BookingServlet()), "/booking");
        
        // Add default servlet LAST to serve static files and JSPs at root
        ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
        holderDefault.setInitParameter("dirAllowed", "true");
        context.addServlet(holderDefault, "/");

        System.out.println("Starting Jetty Server on http://localhost:3000");
        System.out.println("Access the application at http://localhost:3000");
        System.out.println("Demo credentials - Username: demo, Password: demo123");

        try {
            // Start the server
            server.start();
            System.out.println("=================================");
            System.out.println("🎬 GoFDFS Movie Booking Server Started!");
            System.out.println("=================================");
            System.out.println("Access the application at:");
            System.out.println("  http://localhost:3000");
            System.out.println("  http://localhost:3000/movies");
            System.out.println("=================================");
            System.out.println("Press ENTER to stop the server");
            System.out.println("=================================");
            
            // Wait for user input to keep server alive
            System.in.read();
            
            System.out.println("\nStopping server...");
            server.stop();
            System.out.println("Server stopped successfully.");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to start server!");
            e.printStackTrace();
        }
    }
}