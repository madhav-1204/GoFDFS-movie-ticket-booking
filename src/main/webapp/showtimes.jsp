<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.movieticket.core.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Showtimes - Movie Ticket Booking</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>🎬 Movie Ticket Booking System</h1>
            <nav>
                <a href="./">Home</a>
                <a href="movies">Movies</a>
                <% if (session.getAttribute("user") != null) { %>
                    <a href="booking?action=mybookings">My Bookings</a>
                    <span class="user-info">Welcome, <%= session.getAttribute("username") %>!</span>
                <% } %>
            </nav>
        </header>
        
        <main>
            <%
                Movie movie = (Movie) request.getAttribute("movie");
                List<Showtime> showtimes = (List<Showtime>) request.getAttribute("showtimes");
            %>
            
            <h2>Showtimes for <%= movie.getTitle() %></h2>
            
            <div class="movie-details">
                <img src="<%= movie.getPosterUrl() %>" alt="<%= movie.getTitle() %>" class="movie-poster-large">
                <div>
                    <p><strong>Genre:</strong> <%= movie.getGenre() %></p>
                    <p><strong>Rating:</strong> <%= movie.getRating() %></p>
                    <p><strong>Duration:</strong> <%= movie.getDuration() %> minutes</p>
                    <p><strong>Description:</strong> <%= movie.getDescription() %></p>
                </div>
            </div>
            
            <h3>Available Showtimes</h3>
            <div class="showtimes-list">
                <%
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a");
                    if (showtimes != null && !showtimes.isEmpty()) {
                        for (Showtime showtime : showtimes) {
                %>
                    <div class="showtime-card">
                        <div class="showtime-info">
                            <p class="time"><strong><%= showtime.getStartTime().format(formatter) %></strong></p>
                            <p class="theater">Theater: <%= showtime.getTheater().getName() %></p>
                            <p class="seats">Available Seats: <%= showtime.getAvailableSeatsCount() %> / <%= showtime.getTheater().getTotalSeats() %></p>
                        </div>
                        <a href="booking?action=seats&showtimeId=<%= showtime.getId() %>" class="btn btn-primary">Select Seats</a>
                    </div>
                <%
                        }
                    } else {
                %>
                    <p>No showtimes available for this movie.</p>
                <%
                    }
                %>
            </div>
        </main>
        
        <footer>
            <p>&copy; 2025 Movie Ticket Booking System. All rights reserved.</p>
        </footer>
    </div>
</body>
</html>
