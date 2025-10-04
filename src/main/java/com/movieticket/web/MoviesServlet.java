package com.movieticket.web;

import com.movieticket.core.CinemaSystem;
import com.movieticket.core.Movie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MoviesServlet extends HttpServlet {
    private CinemaSystem cinemaSystem;
    
    @Override
    public void init() throws ServletException {
        cinemaSystem = CinemaSystem.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get all movies
        List<Movie> movies = cinemaSystem.getAllMovies();
        
        // Generate HTML directly
        resp.setContentType("text/html;charset=UTF-8");
        java.io.PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>Movies in Hyderabad - Book Movie Tickets</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Roboto', Arial, sans-serif; background: #f5f5f5; }");
        out.println(".header { background: #333545; color: white; padding: 15px 0; position: sticky; top: 0; z-index: 100; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".header-content { max-width: 1200px; margin: 0 auto; padding: 0 20px; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".logo { font-size: 1.5em; font-weight: bold; color: #f84464; }");
        out.println(".nav-links { display: flex; gap: 20px; }");
        out.println(".nav-links a { color: white; text-decoration: none; font-size: 0.9em; transition: color 0.2s; }");
        out.println(".nav-links a:hover { color: #f84464; }");
        out.println(".container { max-width: 1200px; margin: 0 auto; padding: 30px 20px; }");
        out.println(".section-title { font-size: 1.5em; font-weight: 600; color: #333; margin-bottom: 20px; }");
        out.println(".movies-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(180px, 1fr)); gap: 20px; }");
        out.println(".movie-card { background: white; border-radius: 8px; overflow: hidden; cursor: pointer; transition: transform 0.2s; }");
        out.println(".movie-card:hover { transform: scale(1.03); }");
        out.println(".movie-poster { width: 100%; height: 280px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); display: flex; align-items: center; justify-content: center; color: white; font-size: 2.5em; font-weight: bold; }");
        out.println(".movie-info { padding: 12px; }");
        out.println(".movie-title { font-size: 1em; font-weight: 600; color: #333; margin-bottom: 6px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }");
        out.println(".movie-meta { font-size: 0.85em; color: #666; margin-bottom: 4px; }");
        out.println(".movie-genre { display: inline-block; background: #f5f5f5; padding: 3px 8px; border-radius: 4px; font-size: 0.75em; color: #666; margin-right: 5px; }");
        out.println(".movie-rating { color: #333; font-weight: 500; }");
        out.println(".rating-icon { color: #f84464; }");
        out.println(".book-btn { display: block; width: 100%; padding: 10px; background: #f84464; color: white; text-align: center; text-decoration: none; border-radius: 6px; margin-top: 10px; font-size: 0.9em; font-weight: 500; transition: background 0.2s; }");
        out.println(".book-btn:hover { background: #d63651; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='header'>");
        out.println("<div class='header-content'>");
        out.println("<div class='logo'>GoFDFS</div>");
        out.println("<div class='nav-links'>");
        out.println("<a href='./'>Home</a>");
        out.println("<a href='movies'>Movies</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        
        out.println("<div class='container'>");
        out.println("<div class='section-title'>Movies in Hyderabad</div>");
        
        if (movies == null || movies.isEmpty()) {
            out.println("<div style='background: white; padding: 40px; border-radius: 8px; text-align: center; color: #666;'>");
            out.println("<p>No movies currently available.</p>");
            out.println("</div>");
        } else {
            out.println("<div class='movies-grid'>");
            for (Movie movie : movies) {
                String firstLetter = movie.getTitle().substring(0, 1).toUpperCase();
                out.println("<div class='movie-card'>");
                out.println("<div class='movie-poster'>" + firstLetter + "</div>");
                out.println("<div class='movie-info'>");
                out.println("<div class='movie-title'>" + movie.getTitle() + "</div>");
                out.println("<div class='movie-meta'>");
                out.println("<span class='movie-genre'>" + movie.getGenre() + "</span>");
                out.println("</div>");
                out.println("<div class='movie-meta'>");
                out.println("<span class='rating-icon'>★</span> <span class='movie-rating'>" + movie.getRating() + "/10</span>");
                out.println(" • " + movie.getDuration() + " mins");
                out.println("</div>");
                out.println("<a href='booking?action=showtimes&movieId=" + movie.getId() + "' class='book-btn'>Book</a>");
                out.println("</div>");
                out.println("</div>");
            }
            out.println("</div>");
        }
        
        out.println("</div>");
        out.println("</body></html>");
    }
}
