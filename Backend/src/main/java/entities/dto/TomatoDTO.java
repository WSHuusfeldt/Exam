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
public class TomatoDTO {
    
    private String title;
    private String source;
    private ViewerDTO viewer;
    private ViewerDTO critic;

    public TomatoDTO() {
    }

    public TomatoDTO(String title, String source, ViewerDTO viewer, ViewerDTO critic) {
        this.title = title;
        this.source = source;
        this.viewer = viewer;
        this.critic = critic;
    }

    public ViewerDTO getViewer() {
        return viewer;
    }

    public void setViewer(ViewerDTO viewer) {
        this.viewer = viewer;
    }

    public ViewerDTO getCritic() {
        return critic;
    }

    public void setCritic(ViewerDTO critic) {
        this.critic = critic;
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

    
    
    
}
