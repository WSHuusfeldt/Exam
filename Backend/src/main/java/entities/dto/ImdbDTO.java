/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.dto;

/**
 *
 * @author William
 */
public class ImdbDTO {
    
    private String title;
    private String source;
    private double imdbRating;
    private int imdbVotes;

    public ImdbDTO() {
    }

    public ImdbDTO(String title, String source, double imdbRating, int imdbVotes) {
        this.title = title;
        this.source = source;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(int imdbVotes) {
        this.imdbVotes = imdbVotes;
    }
    
    
}
