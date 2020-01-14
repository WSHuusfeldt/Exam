/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.dto;

/**
 *
 * @author APC
 */
public class ViewerDTO {
    
    private double rating;
    private int numReviews;
    private int meter;

    public ViewerDTO() {
    }

    public ViewerDTO(double rating, int numReviews, int meter) {
        this.rating = rating;
        this.numReviews = numReviews;
        this.meter = meter;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public int getMeter() {
        return meter;
    }

    public void setMeter(int meter) {
        this.meter = meter;
    }
    
    
    
}
