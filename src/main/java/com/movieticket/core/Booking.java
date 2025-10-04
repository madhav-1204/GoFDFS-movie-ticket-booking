package com.movieticket.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private User user;
    private Showtime showtime;
    private List<Seat> bookedSeats;
    private LocalDateTime bookingTime;
    private double totalAmount;
    private String bookingReference;
    
    public Booking() {
        this.bookedSeats = new ArrayList<>();
    }
    
    public Booking(int id, User user, Showtime showtime, List<Seat> bookedSeats) {
        this.id = id;
        this.user = user;
        this.showtime = showtime;
        this.bookedSeats = bookedSeats;
        this.bookingTime = LocalDateTime.now();
        this.totalAmount = calculateTotal();
        this.bookingReference = generateReference();
    }
    
    private double calculateTotal() {
        return bookedSeats.stream()
                .mapToDouble(Seat::getPrice)
                .sum();
    }
    
    private String generateReference() {
        return "BK" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Showtime getShowtime() {
        return showtime;
    }
    
    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }
    
    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }
    
    public void setBookedSeats(List<Seat> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getBookingReference() {
        return bookingReference;
    }
    
    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", showtime=" + showtime.getMovie().getTitle() +
                ", seats=" + bookedSeats.size() +
                ", totalAmount=" + totalAmount +
                ", reference='" + bookingReference + '\'' +
                '}';
    }
}
