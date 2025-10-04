package com.movieticket.core;

import java.io.Serializable;

public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String seatNumber;
    private String rowLetter;
    private int seatInRow;
    private boolean isBooked;
    private double price;
    
    public Seat() {
    }
    
    public Seat(int id, String seatNumber, String rowLetter, int seatInRow, double price) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.rowLetter = rowLetter;
        this.seatInRow = seatInRow;
        this.isBooked = false;
        this.price = price;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public String getRowLetter() {
        return rowLetter;
    }
    
    public void setRowLetter(String rowLetter) {
        this.rowLetter = rowLetter;
    }
    
    public int getSeatInRow() {
        return seatInRow;
    }
    
    public void setSeatInRow(int seatInRow) {
        this.seatInRow = seatInRow;
    }
    
    public boolean isBooked() {
        return isBooked;
    }
    
    public void setBooked(boolean booked) {
        isBooked = booked;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber='" + seatNumber + '\'' +
                ", isBooked=" + isBooked +
                ", price=" + price +
                '}';
    }
}
