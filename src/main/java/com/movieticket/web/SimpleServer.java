package com.movieticket.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SimpleServer {
    public static void main(String[] args) {
        Server server = new Server(3000);
        
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);
        
        context.addServlet(new ServletHolder(new MoviesServlet()), "/movies");
        context.addServlet(new ServletHolder(new BookingServlet()), "/booking");
        
        try {
            server.start();
            System.out.println("=================================");
            System.out.println("Server started on http://localhost:3000");
            System.out.println("Press ENTER to stop the server");
            System.out.println("=================================");
            
            // Wait for user input to keep server alive
            System.in.read();
            
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
