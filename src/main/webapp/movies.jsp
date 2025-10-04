<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.movieticket.core.Movie" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Movies - Hyderabad Cinemas</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0;
        }
        .header {
            background: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .header h1 {
            margin: 0 0 10px 0;
            color: #667eea;
            font-size: 2.5em;
        }
        .nav-links {
            display: flex;
            gap: 15px;
            margin-top: 15px;
        }
        .nav-links a {
            color: #667eea;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 5px;
            background: #f0f0f0;
            transition: all 0.3s;
        }
        .nav-links a:hover {
            background: #667eea;
            color: white;
        }
        .movies-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
            padding: 0;
            list-style: none;
        }
        .movie-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .movie-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 15px rgba(0,0,0,0.2);
        }
        .movie-poster {
            width: 100%;
            height: 400px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 3em;
            font-weight: bold;
        }
        .movie-info {
            padding: 20px;
        }
        .movie-title {
            font-size: 1.4em;
            font-weight: bold;
            color: #333;
            margin: 0 0 10px 0;
        }
        .movie-meta {
            color: #666;
            font-size: 0.95em;
            margin: 5px 0;
        }
        .movie-genre {
            display: inline-block;
            background: #f0f0f0;
            padding: 4px 12px;
            border-radius: 15px;
            font-size: 0.85em;
            color: #667eea;
            margin-right: 8px;
        }
        .movie-rating {
            color: #ffa500;
            font-weight: bold;
        }
        .book-btn {
            display: block;
            width: 100%;
            padding: 12px;
            background: #667eea;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 6px;
            margin-top: 15px;
            font-weight: bold;
            transition: background 0.3s;
        }
        .book-btn:hover {
            background: #764ba2;
        }
        .no-movies {
            background: white;
            padding: 40px;
            border-radius: 10px;
            text-align: center;
            font-size: 1.2em;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🎬 Now Showing in Hyderabad</h1>
            <div class="nav-links">
                <a href="./">🏠 Home</a>
                <% if (session.getAttribute("user") != null) { %>
                    <a href="booking?action=mybookings">📋 My Bookings</a>
                    <a href="login">🚪 Logout</a>
                <% } else { %>
                    <a href="login">🔐 Login</a>
                    <a href="register">📝 Register</a>
                <% } %>
            </div>
        </div>

        <%
            List<Movie> movies = (List<Movie>) request.getAttribute("movies");
            if (movies == null || movies.isEmpty()) {
        %>
            <div class="no-movies">
                <p>🎥 No movies currently available.</p>
            </div>
        <%
            } else {
        %>
            <ul class="movies-grid">
                <%
                    for (Movie movie : movies) {
                        String firstLetter = movie.getTitle().substring(0, 1).toUpperCase();
                %>
                    <li class="movie-card">
                        <div class="movie-poster"><%= firstLetter %></div>
                        <div class="movie-info">
                            <div class="movie-title"><%= movie.getTitle() %></div>
                            <div class="movie-meta">
                                <span class="movie-genre"><%= movie.getGenre() %></span>
                                <span class="movie-rating">⭐ <%= movie.getRating() %>/10</span>
                            </div>
                            <div class="movie-meta">⏱️ <%= movie.getDuration() %> minutes</div>
                            <a href="booking?action=showtimes&movieId=<%= movie.getId() %>" class="book-btn">
                                🎟️ Book Tickets
                            </a>
                        </div>
                    </li>
                <%
                    }
                %>
            </ul>
        <%
            }
        %>
    </div>
</body>
</html>
