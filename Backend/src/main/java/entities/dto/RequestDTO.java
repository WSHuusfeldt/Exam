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
public class RequestDTO {
    
    private String title;
    private Long requestAmount;

    public RequestDTO() {
    }

    public RequestDTO(String title, Long requestAmount) {
        this.title = title;
        this.requestAmount = requestAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(Long requestAmount) {
        this.requestAmount = requestAmount;
    }
    
    
    
}
