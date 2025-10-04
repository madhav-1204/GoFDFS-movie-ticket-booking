<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.movieticket.core.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings - Movie Ticket Booking</title>
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
            <h2>My Bookings</h2>
            
            <%
                List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a");
                
                if (bookings != null && !bookings.isEmpty()) {
                    for (Booking booking : bookings) {
            %>
                <div class="booking-card">
                    <div class="booking-header">
                        <h3><%= booking.getShowtime().getMovie().getTitle() %></h3>
                        <span class="booking-ref">Ref: <%= booking.getBookingReference() %></span>
                    </div>
                    <div class="booking-details">
                        <p><strong>Theater:</strong> <%= booking.getShowtime().getTheater().getName() %></p>
                        <p><strong>Showtime:</strong> <%= booking.getShowtime().getStartTime().format(formatter) %></p>
                        <p><strong>Booked On:</strong> <%= booking.getBookingTime().format(formatter) %></p>
                        <p><strong>Seats:</strong> 
                            <% 
                                for (int i = 0; i < booking.getBookedSeats().size(); i++) {
                                    out.print(booking.getBookedSeats().get(i).getSeatNumber());
                                    if (i < booking.getBookedSeats().size() - 1) out.print(", ");
                                }
                            %>
                        </p>
                        <p class="total-amount"><strong>Total Paid:</strong> $<%= String.format("%.2f", booking.getTotalAmount()) %></p>
                    </div>
                </div>
            <%
                    }
                } else {
            %>
                <div class="empty-state">
                    <p>You haven't made any bookings yet.</p>
                    <a href="movies" class="btn btn-primary">Browse Movies</a>
                </div>
            <%
                }
            %>
        </main>
        
        <footer>
            <p>&copy; 2025 Movie Ticket Booking System. All rights reserved.</p>
        </footer>
    </div>
</body>
</html>
