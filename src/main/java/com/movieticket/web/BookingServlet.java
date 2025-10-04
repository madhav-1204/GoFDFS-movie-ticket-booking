package com.movieticket.web;

import com.movieticket.core.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BookingServlet extends HttpServlet {
    private CinemaSystem cinemaSystem;
    
    @Override
    public void init() throws ServletException {
        cinemaSystem = CinemaSystem.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        
        if ("showtimes".equals(action)) {
            // Show showtimes for a movie
            int movieId = Integer.parseInt(req.getParameter("movieId"));
            Movie movie = cinemaSystem.getMovieById(movieId);
            List<Showtime> showtimes = cinemaSystem.getShowtimesByMovie(movieId);
            
            renderShowtimesPage(resp, movie, showtimes);
            
        } else if ("seats".equals(action)) {
            // Show seats for a showtime
            int showtimeId = Integer.parseInt(req.getParameter("showtimeId"));
            Showtime showtime = cinemaSystem.getShowtimeById(showtimeId);
            
            renderSeatsPage(resp, showtime, null);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Process booking without authentication - for now
        // TODO: Add Supabase/Firebase authentication later
        
        int showtimeId = Integer.parseInt(req.getParameter("showtimeId"));
        String[] selectedSeats = req.getParameterValues("seats");
        
        if (selectedSeats == null || selectedSeats.length == 0) {
            Showtime showtime = cinemaSystem.getShowtimeById(showtimeId);
            renderSeatsPage(resp, showtime, "Please select at least one seat");
            return;
        }
        
        Showtime showtime = cinemaSystem.getShowtimeById(showtimeId);
        List<String> seatNumbers = Arrays.asList(selectedSeats);
        
        // Create a guest user for now
        User guestUser = new User(0, "guest", "guest", "guest@gofdfs.com", "Guest User", "0000000000");
        Booking booking = cinemaSystem.createBooking(guestUser, showtime, seatNumbers);
        
        if (booking != null) {
            // Booking successful
            renderBookingConfirmationPage(resp, booking);
        } else {
            // Booking failed
            renderSeatsPage(resp, showtime, "Selected seats are no longer available");
        }
    }
    
    private void renderShowtimesPage(HttpServletResponse resp, Movie movie, List<Showtime> showtimes) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        java.io.PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>" + movie.getTitle() + " - Book Tickets</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Roboto', Arial, sans-serif; background: #f5f5f5; }");
        out.println(".header { background: #333545; color: white; padding: 15px 0; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".header-content { max-width: 1200px; margin: 0 auto; padding: 0 20px; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".logo { font-size: 1.5em; font-weight: bold; color: #f84464; text-decoration: none; }");
        out.println(".nav-links { display: flex; gap: 20px; }");
        out.println(".nav-links a { color: white; text-decoration: none; font-size: 0.9em; transition: color 0.2s; }");
        out.println(".nav-links a:hover { color: #f84464; }");
        out.println(".container { max-width: 1000px; margin: 0 auto; padding: 30px 20px; }");
        out.println(".back-link { color: #666; text-decoration: none; font-size: 0.9em; margin-bottom: 20px; display: inline-block; }");
        out.println(".back-link:hover { color: #f84464; }");
        out.println(".movie-header { background: white; padding: 30px; border-radius: 8px; margin-bottom: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".movie-title { font-size: 2em; color: #333; margin-bottom: 10px; }");
        out.println(".movie-details { color: #666; font-size: 0.95em; }");
        out.println(".movie-details span { margin-right: 20px; }");
        out.println(".section-title { font-size: 1.3em; font-weight: 600; color: #333; margin-bottom: 20px; }");
        out.println(".theater-card { background: white; border-radius: 8px; padding: 25px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".theater-name { font-size: 1.2em; font-weight: 600; color: #333; margin-bottom: 15px; }");
        out.println(".showtimes { display: flex; flex-wrap: wrap; gap: 12px; }");
        out.println(".showtime-btn { padding: 12px 20px; background: #f5f5f5; border: 2px solid #e0e0e0; border-radius: 6px; color: #333; text-decoration: none; font-weight: 500; transition: all 0.2s; display: inline-block; }");
        out.println(".showtime-btn:hover { background: #f84464; color: white; border-color: #f84464; }");
        out.println(".showtime-time { font-size: 1em; display: block; }");
        out.println(".showtime-seats { font-size: 0.8em; color: #666; display: block; margin-top: 4px; }");
        out.println(".no-showtimes { background: white; padding: 40px; border-radius: 8px; text-align: center; color: #666; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='header'>");
        out.println("<div class='header-content'>");
        out.println("<a href='./' class='logo'>GoFDFS</a>");
        out.println("<div class='nav-links'>");
        out.println("<a href='./'>Home</a>");
        out.println("<a href='movies'>Movies</a>");
        out.println("</div></div></div>");
        
        out.println("<div class='container'>");
        out.println("<a href='movies' class='back-link'>← Back to Movies</a>");
        
        out.println("<div class='movie-header'>");
        out.println("<div class='movie-title'>" + movie.getTitle() + "</div>");
        out.println("<div class='movie-details'>");
        out.println("<span>★ " + movie.getRating() + "/10</span>");
        out.println("<span>" + movie.getGenre() + "</span>");
        out.println("<span>" + movie.getDuration() + " mins</span>");
        out.println("</div>");
        out.println("</div>");
        
        if (showtimes == null || showtimes.isEmpty()) {
            out.println("<div class='no-showtimes'>");
            out.println("<p>No showtimes available for this movie.</p>");
            out.println("</div>");
        } else {
            out.println("<div class='section-title'>Select Showtime</div>");
            
            // Group showtimes by theater
            java.util.Map<Theater, java.util.List<Showtime>> showtimesByTheater = new java.util.HashMap<>();
            for (Showtime st : showtimes) {
                showtimesByTheater.computeIfAbsent(st.getTheater(), k -> new java.util.ArrayList<>()).add(st);
            }
            
            for (java.util.Map.Entry<Theater, java.util.List<Showtime>> entry : showtimesByTheater.entrySet()) {
                Theater theater = entry.getKey();
                java.util.List<Showtime> theaterShowtimes = entry.getValue();
                
                out.println("<div class='theater-card'>");
                out.println("<div class='theater-name'>" + theater.getName() + "</div>");
                out.println("<div class='showtimes'>");
                
                for (Showtime st : theaterShowtimes) {
                    int availableSeats = st.getAvailableSeatsCount();
                    String time = st.getStartTime().toLocalTime().toString();
                    
                    out.println("<a href='booking?action=seats&showtimeId=" + st.getId() + "' class='showtime-btn'>");
                    out.println("<span class='showtime-time'>" + time + "</span>");
                    out.println("<span class='showtime-seats'>" + availableSeats + " seats</span>");
                    out.println("</a>");
                }
                
                out.println("</div>");
                out.println("</div>");
            }
        }
        
        out.println("</div>");
        out.println("</body></html>");
    }
    
    private void renderSeatsPage(HttpServletResponse resp, Showtime showtime, String error) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        java.io.PrintWriter out = resp.getWriter();
        
        Movie movie = showtime.getMovie();
        Theater theater = showtime.getTheater();
        List<Seat> seats = showtime.getSeats();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>Select Seats - " + movie.getTitle() + "</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Roboto', Arial, sans-serif; background: #f5f5f5; }");
        out.println(".header { background: #333545; color: white; padding: 15px 0; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".header-content { max-width: 1200px; margin: 0 auto; padding: 0 20px; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".logo { font-size: 1.5em; font-weight: bold; color: #f84464; text-decoration: none; }");
        out.println(".container { max-width: 900px; margin: 0 auto; padding: 30px 20px; }");
        out.println(".back-link { color: #666; text-decoration: none; font-size: 0.9em; margin-bottom: 20px; display: inline-block; }");
        out.println(".back-link:hover { color: #f84464; }");
        out.println(".movie-info { background: white; padding: 20px; border-radius: 8px; margin-bottom: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".movie-title { font-size: 1.5em; font-weight: 600; color: #333; margin-bottom: 8px; }");
        out.println(".showtime-info { color: #666; font-size: 0.95em; }");
        out.println(".showtime-info span { margin-right: 15px; }");
        out.println(".screen { background: white; padding: 30px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }");
        out.println(".screen-label { text-align: center; background: #333; color: white; padding: 10px; border-radius: 4px; margin-bottom: 30px; font-size: 0.9em; }");
        out.println(".seats-container { display: flex; flex-direction: column; gap: 8px; align-items: center; }");
        out.println(".seat-row { display: flex; gap: 8px; align-items: center; }");
        out.println(".row-label { width: 30px; text-align: center; font-weight: 600; color: #666; font-size: 0.9em; }");
        out.println(".seat { width: 35px; height: 35px; border: 2px solid #ddd; border-radius: 6px; background: #fff; cursor: pointer; transition: all 0.2s; display: flex; align-items: center; justify-content: center; font-size: 0.75em; color: #999; }");
        out.println(".seat:hover { border-color: #f84464; transform: scale(1.1); }");
        out.println(".seat.booked { background: #e0e0e0; border-color: #999; cursor: not-allowed; color: #666; }");
        out.println(".seat.booked:hover { transform: none; }");
        out.println(".seat.selected { background: #f84464; border-color: #f84464; color: white; }");
        out.println(".legend { display: flex; justify-content: center; gap: 30px; margin-top: 20px; padding-top: 20px; border-top: 1px solid #e0e0e0; }");
        out.println(".legend-item { display: flex; align-items: center; gap: 8px; font-size: 0.9em; color: #666; }");
        out.println(".legend-box { width: 25px; height: 25px; border-radius: 4px; border: 2px solid #ddd; }");
        out.println(".legend-box.available { background: #fff; }");
        out.println(".legend-box.selected { background: #f84464; border-color: #f84464; }");
        out.println(".legend-box.booked { background: #e0e0e0; border-color: #999; }");
        out.println(".booking-summary { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); position: sticky; bottom: 20px; }");
        out.println(".summary-content { display: flex; justify-content: space-between; align-items: center; }");
        out.println(".selected-seats { font-size: 0.95em; color: #666; }");
        out.println(".selected-seats strong { color: #333; font-size: 1.1em; display: block; margin-bottom: 5px; }");
        out.println(".total-price { font-size: 1.5em; font-weight: 600; color: #333; }");
        out.println(".book-btn { padding: 15px 40px; background: #f84464; color: white; border: none; border-radius: 6px; font-size: 1.1em; font-weight: 600; cursor: pointer; transition: background 0.2s; }");
        out.println(".book-btn:hover { background: #d63651; }");
        out.println(".book-btn:disabled { background: #ccc; cursor: not-allowed; }");
        out.println(".error { background: #fee; border: 1px solid #fcc; color: #c33; padding: 12px; border-radius: 6px; margin-bottom: 20px; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='header'>");
        out.println("<div class='header-content'>");
        out.println("<a href='./' class='logo'>GoFDFS</a>");
        out.println("</div></div>");
        
        out.println("<div class='container'>");
        out.println("<a href='booking?action=showtimes&movieId=" + movie.getId() + "' class='back-link'>← Back to Showtimes</a>");
        
        if (error != null) {
            out.println("<div class='error'>" + error + "</div>");
        }
        
        out.println("<div class='movie-info'>");
        out.println("<div class='movie-title'>" + movie.getTitle() + "</div>");
        out.println("<div class='showtime-info'>");
        out.println("<span>🏛️ " + theater.getName() + "</span>");
        out.println("<span>🕐 " + showtime.getStartTime().toLocalTime() + "</span>");
        out.println("<span>📅 " + showtime.getStartTime().toLocalDate() + "</span>");
        out.println("</div></div>");
        
        out.println("<div class='screen'>");
        out.println("<div class='screen-label'>SCREEN THIS WAY</div>");
        out.println("<div class='seats-container'>");
        
        // Group seats by row
        java.util.Map<String, java.util.List<Seat>> seatsByRow = new java.util.LinkedHashMap<>();
        for (Seat seat : seats) {
            seatsByRow.computeIfAbsent(seat.getRowLetter(), k -> new java.util.ArrayList<>()).add(seat);
        }
        
        out.println("<form method='POST' action='booking' id='seatForm'>");
        out.println("<input type='hidden' name='showtimeId' value='" + showtime.getId() + "'>");
        
        for (java.util.Map.Entry<String, java.util.List<Seat>> entry : seatsByRow.entrySet()) {
            String row = entry.getKey();
            java.util.List<Seat> rowSeats = entry.getValue();
            
            out.println("<div class='seat-row'>");
            out.println("<div class='row-label'>" + row + "</div>");
            
            for (Seat seat : rowSeats) {
                String seatClass = "seat";
                if (seat.isBooked()) {
                    seatClass += " booked";
                }
                
                if (seat.isBooked()) {
                    out.println("<div class='" + seatClass + "' title='Booked'>×</div>");
                } else {
                    out.println("<input type='checkbox' name='seats' value='" + seat.getSeatNumber() + "' style='display:none;' class='seat-checkbox' data-price='" + seat.getPrice() + "' id='seat-" + seat.getSeatNumber() + "'>");
                    out.println("<div class='" + seatClass + " clickable-seat' title='" + seat.getSeatNumber() + " - ₹" + String.format("%.0f", seat.getPrice()) + "' data-seat='" + seat.getSeatNumber() + "'>" + seat.getSeatInRow() + "</div>");
                }
            }
            
            out.println("</div>");
        }
        
        out.println("</form>");
        out.println("</div>");
        
        out.println("<div class='legend'>");
        out.println("<div class='legend-item'><div class='legend-box available'></div><span>Available</span></div>");
        out.println("<div class='legend-item'><div class='legend-box selected'></div><span>Selected</span></div>");
        out.println("<div class='legend-item'><div class='legend-box booked'></div><span>Booked</span></div>");
        out.println("</div>");
        
        out.println("</div>");
        
        out.println("<div class='booking-summary'>");
        out.println("<div class='summary-content'>");
        out.println("<div class='selected-seats'>");
        out.println("<strong id='seatCount'>0 Seats Selected</strong>");
        out.println("<span id='seatNumbers'>Select your seats</span>");
        out.println("</div>");
        out.println("<div class='total-price'>₹<span id='totalPrice'>0</span></div>");
        out.println("<button type='button' class='book-btn' id='bookBtn' disabled onclick='document.getElementById(\"seatForm\").submit()'>Proceed to Book</button>");
        out.println("</div></div>");
        
        out.println("</div>");
        
        // JavaScript for interactive seat selection
        out.println("<script>");
        out.println("document.querySelectorAll('.clickable-seat').forEach(seatDiv => {");
        out.println("  seatDiv.style.cursor = 'pointer';");
        out.println("  seatDiv.addEventListener('click', function() {");
        out.println("    const seatNumber = this.dataset.seat;");
        out.println("    const checkbox = document.getElementById('seat-' + seatNumber);");
        out.println("    if (checkbox) {");
        out.println("      checkbox.checked = !checkbox.checked;");
        out.println("      if (checkbox.checked) {");
        out.println("        this.classList.add('selected');");
        out.println("      } else {");
        out.println("        this.classList.remove('selected');");
        out.println("      }");
        out.println("      updateSummary();");
        out.println("    }");
        out.println("  });");
        out.println("});");
        out.println("function updateSummary() {");
        out.println("  const selected = Array.from(document.querySelectorAll('.seat-checkbox:checked'));");
        out.println("  const count = selected.length;");
        out.println("  const seatNumbers = selected.map(cb => cb.value).join(', ');");
        out.println("  const total = selected.reduce((sum, cb) => sum + parseFloat(cb.dataset.price), 0);");
        out.println("  document.getElementById('seatCount').textContent = count + ' Seat' + (count !== 1 ? 's' : '') + ' Selected';");
        out.println("  document.getElementById('seatNumbers').textContent = count > 0 ? seatNumbers : 'Select your seats';");
        out.println("  document.getElementById('totalPrice').textContent = Math.round(total);");
        out.println("  document.getElementById('bookBtn').disabled = count === 0;");
        out.println("}");
        out.println("</script>");
        
        out.println("</body></html>");
    }
    
    private void renderBookingConfirmationPage(HttpServletResponse resp, Booking booking) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        java.io.PrintWriter out = resp.getWriter();
        
        Showtime showtime = booking.getShowtime();
        Movie movie = showtime.getMovie();
        Theater theater = showtime.getTheater();
        User user = booking.getUser();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Booking Confirmed - GoFDFS</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: Arial, sans-serif; background: #f5f5f5; }");
        out.println("header { background: #333545; padding: 15px 50px; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".logo { color: #f84464; font-size: 24px; font-weight: bold; text-decoration: none; }");
        out.println(".nav-links { display: flex; gap: 30px; }");
        out.println(".nav-links a { color: white; text-decoration: none; }");
        out.println(".nav-links a:hover { color: #f84464; }");
        out.println(".confirmation-container { max-width: 600px; margin: 50px auto; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }");
        out.println(".success-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 40px; text-align: center; }");
        out.println(".success-icon { font-size: 60px; margin-bottom: 20px; }");
        out.println(".success-header h1 { font-size: 28px; margin-bottom: 10px; }");
        out.println(".success-header p { font-size: 16px; opacity: 0.9; }");
        out.println(".booking-details { padding: 30px; }");
        out.println(".detail-row { display: flex; justify-content: space-between; padding: 15px 0; border-bottom: 1px solid #eee; }");
        out.println(".detail-row:last-child { border-bottom: none; }");
        out.println(".detail-label { color: #666; font-weight: 500; }");
        out.println(".detail-value { color: #333; font-weight: 600; text-align: right; }");
        out.println(".seats-list { color: #f84464; }");
        out.println(".total-amount { font-size: 20px; color: #667eea; }");
        out.println(".email-notice { background: #e8f4fd; padding: 20px; margin: 20px 30px; border-radius: 8px; text-align: center; color: #0066cc; }");
        out.println(".email-notice strong { display: block; margin-bottom: 5px; font-size: 16px; }");
        out.println(".action-buttons { display: flex; gap: 15px; padding: 0 30px 30px; }");
        out.println(".btn { flex: 1; padding: 12px; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; text-decoration: none; text-align: center; display: block; }");
        out.println(".btn-primary { background: #f84464; color: white; }");
        out.println(".btn-primary:hover { background: #e73454; }");
        out.println(".btn-secondary { background: white; color: #333; border: 2px solid #ddd; }");
        out.println(".btn-secondary:hover { border-color: #f84464; color: #f84464; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        // Header
        out.println("<header>");
        out.println("<a href='movies' class='logo'>🎬 GoFDFS</a>");
        out.println("<div class='nav-links'>");
        out.println("<a href='movies'>Movies</a>");
        out.println("</div>");
        out.println("</header>");
        
        // Success Message
        out.println("<div class='confirmation-container'>");
        out.println("<div class='success-header'>");
        out.println("<div class='success-icon'>✓</div>");
        out.println("<h1>Booking Confirmed!</h1>");
        out.println("<p>Your tickets have been booked successfully</p>");
        out.println("</div>");
        
        // Booking Details
        out.println("<div class='booking-details'>");
        out.println("<div class='detail-row'>");
        out.println("<span class='detail-label'>Booking ID</span>");
        out.println("<span class='detail-value'>#" + String.format("%06d", booking.getId()) + "</span>");
        out.println("</div>");
        
        out.println("<div class='detail-row'>");
        out.println("<span class='detail-label'>Movie</span>");
        out.println("<span class='detail-value'>" + movie.getTitle() + "</span>");
        out.println("</div>");
        
        out.println("<div class='detail-row'>");
        out.println("<span class='detail-label'>Theater</span>");
        out.println("<span class='detail-value'>" + theater.getName() + "</span>");
        out.println("</div>");
        
        out.println("<div class='detail-row'>");
        out.println("<span class='detail-label'>Show Time</span>");
        out.println("<span class='detail-value'>" + showtime.getStartTime().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mm a")) + "</span>");
        out.println("</div>");
        
        out.println("<div class='detail-row'>");
        out.println("<span class='detail-label'>Seats</span>");
        String seatNumbers = booking.getBookedSeats().stream().map(s -> s.getSeatNumber()).collect(java.util.stream.Collectors.joining(", "));
        out.println("<span class='detail-value seats-list'>" + seatNumbers + "</span>");
        out.println("</div>");
        
        out.println("<div class='detail-row'>");
        out.println("<span class='detail-label'>Number of Seats</span>");
        out.println("<span class='detail-value'>" + booking.getBookedSeats().size() + "</span>");
        out.println("</div>");
        
        out.println("<div class='detail-row'>");
        out.println("<span class='detail-label'>Total Amount</span>");
        out.println("<span class='detail-value total-amount'>₹" + String.format("%.0f", booking.getTotalAmount()) + "</span>");
        out.println("</div>");
        out.println("</div>");
        
        // Email Notice
        out.println("<div class='email-notice'>");
        out.println("<strong>📧 Tickets Sent to Your Email</strong>");
        out.println("<p>Your e-tickets have been sent to <strong>" + user.getEmail() + "</strong></p>");
        out.println("<p style='margin-top: 5px; font-size: 14px;'>Please check your inbox and show the tickets at the theater.</p>");
        out.println("</div>");
        
        // Action Buttons
        out.println("<div class='action-buttons'>");
        out.println("<a href='movies' class='btn btn-primary'>Book More Tickets</a>");
        out.println("</div>");
        
        out.println("</div>");
        
        out.println("</body></html>");
    }
}
