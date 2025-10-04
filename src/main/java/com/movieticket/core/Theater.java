package com.movieticket.core;

import java.io.Serializable;

public class Theater implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private int totalSeats;
    private int seatsPerRow;
    
    public Theater() {
    }
    
    public Theater(int id, String name, int totalSeats, int seatsPerRow) {
        this.id = id;
        this.name = name;
        this.totalSeats = totalSeats;
        this.seatsPerRow = seatsPerRow;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getTotalSeats() {
        return totalSeats;
    }
    
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
    
    public int getSeatsPerRow() {
        return seatsPerRow;
    }
    
    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }
    
    @Override
    public String toString() {
        return "Theater{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalSeats=" + totalSeats +
                ", seatsPerRow=" + seatsPerRow +
                '}';
    }
}
