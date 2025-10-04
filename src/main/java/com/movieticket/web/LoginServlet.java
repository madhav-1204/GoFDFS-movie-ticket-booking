package com.movieticket.web;

import com.movieticket.core.CinemaSystem;
import com.movieticket.core.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private CinemaSystem cinemaSystem;
    
    @Override
    public void init() throws ServletException {
        cinemaSystem = CinemaSystem.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = (String) req.getAttribute("error");
        
        resp.setContentType("text/html;charset=UTF-8");
        java.io.PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>Sign In - GoFDFS</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Roboto', Arial, sans-serif; background: #f5f5f5; }");
        out.println(".header { background: #333545; color: white; padding: 15px 0; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".header-content { max-width: 1200px; margin: 0 auto; padding: 0 20px; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".logo { font-size: 1.5em; font-weight: bold; color: #f84464; text-decoration: none; }");
        out.println(".nav-links { display: flex; gap: 20px; }");
        out.println(".nav-links a { color: white; text-decoration: none; font-size: 0.9em; transition: color 0.2s; }");
        out.println(".nav-links a:hover { color: #f84464; }");
        out.println(".container { max-width: 450px; margin: 60px auto; padding: 0 20px; }");
        out.println(".login-box { background: white; border-radius: 8px; padding: 40px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println("h1 { color: #333; font-size: 2em; margin-bottom: 10px; }");
        out.println(".subtitle { color: #666; margin-bottom: 30px; }");
        out.println(".form-group { margin-bottom: 20px; }");
        out.println("label { display: block; color: #333; font-weight: 500; margin-bottom: 8px; font-size: 0.9em; }");
        out.println("input[type='text'], input[type='password'] { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 6px; font-size: 1em; transition: border-color 0.2s; }");
        out.println("input:focus { outline: none; border-color: #f84464; }");
        out.println(".error { background: #fee; border: 1px solid #fcc; color: #c33; padding: 12px; border-radius: 6px; margin-bottom: 20px; font-size: 0.9em; }");
        out.println(".btn { width: 100%; padding: 14px; background: #f84464; color: white; border: none; border-radius: 6px; font-size: 1em; font-weight: 600; cursor: pointer; transition: background 0.2s; }");
        out.println(".btn:hover { background: #d63651; }");
        out.println(".demo-info { background: #f9f9f9; padding: 15px; border-radius: 6px; margin-bottom: 20px; font-size: 0.85em; color: #666; }");
        out.println(".demo-info strong { color: #333; }");
        out.println(".divider { text-align: center; margin: 25px 0; color: #999; position: relative; }");
        out.println(".divider:before, .divider:after { content: ''; position: absolute; top: 50%; width: 45%; height: 1px; background: #ddd; }");
        out.println(".divider:before { left: 0; }");
        out.println(".divider:after { right: 0; }");
        out.println(".register-link { text-align: center; margin-top: 20px; color: #666; }");
        out.println(".register-link a { color: #f84464; text-decoration: none; font-weight: 600; }");
        out.println(".register-link a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='header'>");
        out.println("<div class='header-content'>");
        out.println("<a href='./' class='logo'>GoFDFS</a>");
        out.println("<div class='nav-links'>");
        out.println("<a href='./'>Home</a>");
        out.println("<a href='movies'>Movies</a>");
        out.println("<a href='register'>Register</a>");
        out.println("</div></div></div>");
        
        out.println("<div class='container'>");
        out.println("<div class='login-box'>");
        out.println("<h1>Sign In</h1>");
        out.println("<p class='subtitle'>Welcome back! Sign in to book your tickets</p>");
        
        if (error != null) {
            out.println("<div class='error'>" + error + "</div>");
        }
        
        out.println("<div class='demo-info'>");
        out.println("📝 <strong>Demo Account:</strong><br>");
        out.println("Username: <strong>demo</strong><br>");
        out.println("Password: <strong>demo123</strong>");
        out.println("</div>");
        
        out.println("<form method='POST' action='login'>");
        out.println("<div class='form-group'>");
        out.println("<label>Username</label>");
        out.println("<input type='text' name='username' required placeholder='Enter your username'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label>Password</label>");
        out.println("<input type='password' name='password' required placeholder='Enter your password'>");
        out.println("</div>");
        out.println("<button type='submit' class='btn'>Sign In</button>");
        out.println("</form>");
        
        out.println("<div class='divider'>or</div>");
        out.println("<div class='register-link'>");
        out.println("Don't have an account? <a href='register'>Create one now</a>");
        out.println("</div>");
        
        out.println("</div></div>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        User user = cinemaSystem.authenticateUser(username, password);
        
        if (user != null) {
            // Login successful
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            
            resp.sendRedirect(req.getContextPath() + "/movies");
        } else {
            // Login failed
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
