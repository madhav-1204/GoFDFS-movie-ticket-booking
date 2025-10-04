<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.movieticket.core.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Select Seats - Movie Ticket Booking</title>
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
            <%
                Showtime showtime = (Showtime) request.getAttribute("showtime");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a");
            %>
            
            <h2>Select Your Seats</h2>
            
            <div class="showtime-summary">
                <h3><%= showtime.getMovie().getTitle() %></h3>
                <p><strong>Theater:</strong> <%= showtime.getTheater().getName() %></p>
                <p><strong>Showtime:</strong> <%= showtime.getStartTime().format(formatter) %></p>
            </div>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <div class="screen">SCREEN</div>
            
            <form action="booking" method="post">
                <input type="hidden" name="showtimeId" value="<%= showtime.getId() %>">
                
                <div class="seats-container">
                    <%
                        java.util.Map<String, java.util.List<Seat>> seatsByRow = new java.util.LinkedHashMap<>();
                        for (Seat seat : showtime.getSeats()) {
                            seatsByRow.computeIfAbsent(seat.getRowLetter(), k -> new java.util.ArrayList<>()).add(seat);
                        }
                        
                        for (java.util.Map.Entry<String, java.util.List<Seat>> entry : seatsByRow.entrySet()) {
                    %>
                        <div class="seat-row">
                            <span class="row-label"><%= entry.getKey() %></span>
                            <div class="row-seats">
                            <%
                                for (Seat seat : entry.getValue()) {
                                    if (seat.isBooked()) {
                            %>
                                <div class="seat booked" title="<%= seat.getSeatNumber() %> - Booked">
                                    <%= seat.getSeatInRow() %>
                                </div>
                            <%
                                    } else {
                            %>
                                <label class="seat available">
                                    <input type="checkbox" name="seats" value="<%= seat.getSeatNumber() %>" 
                                           data-price="<%= seat.getPrice() %>">
                                    <span><%= seat.getSeatInRow() %></span>
                                </label>
                            <%
                                    }
                                }
                            %>
                            </div>
                        </div>
                    <%
                        }
                    %>
                </div>
                
                <div class="legend">
                    <div><span class="seat available-legend"></span> Available</div>
                    <div><span class="seat selected-legend"></span> Selected</div>
                    <div><span class="seat booked-legend"></span> Booked</div>
                </div>
                
                <div class="booking-summary">
                    <p>Selected Seats: <span id="selectedSeats">None</span></p>
                    <p>Total Amount: $<span id="totalAmount">0.00</span></p>
                    <button type="submit" class="btn btn-primary">Confirm Booking</button>
                </div>
            </form>
        </main>
        
        <footer>
            <p>&copy; 2025 Movie Ticket Booking System. All rights reserved.</p>
        </footer>
    </div>
    
    <script src="js/seats.js"></script>
</body>
</html>
