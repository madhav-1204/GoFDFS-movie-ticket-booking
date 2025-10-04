<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.movieticket.core.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation - Movie Ticket Booking</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>🎬 Movie Ticket Booking System</h1>
            <nav>
                <a href="./">Home</a>
                <a href="movies">Movies</a>
                <a href="booking?action=mybookings">My Bookings</a>
                <span class="user-info">Welcome, <%= session.getAttribute("username") %>!</span>
            </nav>
        </header>
        
        <main>
            <div class="confirmation-container">
                <div class="success-icon">✓</div>
                <h2>Booking Confirmed!</h2>
                
                <%
                    Booking booking = (Booking) request.getAttribute("booking");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a");
                %>
                
                <div class="confirmation-details">
                    <p class="booking-ref">Booking Reference: <strong><%= booking.getBookingReference() %></strong></p>
                    
                    <div class="confirmation-section">
                        <h3><%= booking.getShowtime().getMovie().getTitle() %></h3>
                        <p><strong>Theater:</strong> <%= booking.getShowtime().getTheater().getName() %></p>
                        <p><strong>Showtime:</strong> <%= booking.getShowtime().getStartTime().format(formatter) %></p>
                    </div>
                    
                    <div class="confirmation-section">
                        <p><strong>Your Seats:</strong></p>
                        <div class="confirmed-seats">
                            <% for (Seat seat : booking.getBookedSeats()) { %>
                                <span class="seat-badge"><%= seat.getSeatNumber() %></span>
                            <% } %>
                        </div>
                    </div>
                    
                    <div class="confirmation-section">
                        <p class="total-paid"><strong>Total Paid:</strong> $<%= String.format("%.2f", booking.getTotalAmount()) %></p>
                    </div>
                    
                    <p class="info-text">Please arrive at least 15 minutes before the showtime.</p>
                </div>
                
                <div class="confirmation-actions">
                    <a href="booking?action=mybookings" class="btn btn-primary">View My Bookings</a>
                    <a href="movies" class="btn btn-secondary">Book Another Movie</a>
                </div>
            </div>
        </main>
        
        <footer>
            <p>&copy; 2025 Movie Ticket Booking System. All rights reserved.</p>
        </footer>
    </div>
</body>
</html>
