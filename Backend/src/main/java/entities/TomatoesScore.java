/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author William
 */
@Entity
public class TomatoesScore implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String source;
    private List viewer;
    private List critic;

    public TomatoesScore(String title, String source, List viewer, List critic) {
        this.title = title;
        this.source = source;
        this.viewer = viewer;
        this.critic = critic;
    }

    public TomatoesScore() {
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

    public List getViewer() {
        return viewer;
    }

    public void setViewer(List viewer) {
        this.viewer = viewer;
    }

    public List getCritic() {
        return critic;
    }

    public void setCritic(List critic) {
        this.critic = critic;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof TomatoesScore)) {
            return false;
        }
        TomatoesScore other = (TomatoesScore) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TomatoesScore[ id=" + id + " ]";
    }
    
}
