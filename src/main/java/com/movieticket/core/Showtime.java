package com.movieticket.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Showtime implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Movie movie;
    private Theater theater;
    private LocalDateTime startTime;
    private List<Seat> seats;
    
    public Showtime() {
        this.seats = new ArrayList<>();
    }
    
    public Showtime(int id, Movie movie, Theater theater, LocalDateTime startTime) {
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.startTime = startTime;
        this.seats = new ArrayList<>();
        initializeSeats();
    }
    
    private void initializeSeats() {
        int seatId = 1;
        int rows = theater.getTotalSeats() / theater.getSeatsPerRow();
        
        for (int i = 0; i < rows; i++) {
            char rowLetter = (char) ('A' + i);
            for (int j = 1; j <= theater.getSeatsPerRow(); j++) {
                String seatNumber = rowLetter + String.valueOf(j);
                double price = calculatePrice(i);
                seats.add(new Seat(seatId++, seatNumber, String.valueOf(rowLetter), j, price));
            }
        }
    }
    
    private double calculatePrice(int rowIndex) {
        // Calculate total rows
        int totalRows = theater.getTotalSeats() / theater.getSeatsPerRow();
        int recliners = totalRows / 3; // Last 1/3 rows are recliners
        int middle = totalRows - recliners - 2; // Middle rows
        
        // Front rows (first 2 rows): ₹150
        if (rowIndex < 2) {
            return 150.00;
        } 
        // Middle rows: ₹250
        else if (rowIndex < (2 + middle)) {
            return 250.00;
        } 
        // Recliner rows (back rows): ₹350
        else {
            return 350.00;
        }
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    public Theater getTheater() {
        return theater;
    }
    
    public void setTheater(Theater theater) {
        this.theater = theater;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public List<Seat> getSeats() {
        return seats;
    }
    
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
    
    public int getAvailableSeatsCount() {
        return (int) seats.stream().filter(seat -> !seat.isBooked()).count();
    }
    
    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", movie=" + movie.getTitle() +
                ", theater=" + theater.getName() +
                ", startTime=" + startTime +
                ", availableSeats=" + getAvailableSeatsCount() +
                '}';
    }
}
