/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author William
 */
@Entity
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column(length=3000)
    private String jSon;
    private String title;
    private Long movieReqId;

    public Request() {
    }

    public Request(Date timestamp, String jSon, String title) {
        this.timestamp = timestamp;
        this.jSon = jSon;
        this.title = title;
    }
    
    public Request(Date timestamp, String jSon, String title, Long movieReqId) {
        this.timestamp = timestamp;
        this.jSon = jSon;
        this.title = title;
        this.movieReqId = movieReqId;
    }

    public Long getMovieReqId() {
        return movieReqId;
    }

    public void setMovieReqId(Long movieReqId) {
        this.movieReqId = movieReqId;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getjSon() {
        return jSon;
    }

    public void setjSon(String jSon) {
        this.jSon = jSon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Request[ id=" + id + " ]";
    }
    
}
