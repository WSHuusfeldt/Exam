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
public class MetacriticDTO {
    
    private String title;
    private String score;
    private int metacritic;

    public MetacriticDTO() {
    }

    public MetacriticDTO(String title, String score, int metacritic) {
        this.title = title;
        this.score = score;
        this.metacritic = metacritic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(int metacritic) {
        this.metacritic = metacritic;
    }
    
    
    
}
