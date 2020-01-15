/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import entities.Request;
import entities.dto.MovieAllDTO;
import entities.dto.RequestDTO;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author William
 */
public class RequestFacade {

    private static RequestFacade instance;
    private static EntityManagerFactory emf;
    private static final Gson gson = new Gson();

    //Private Constructor to ensure Singleton
    private RequestFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static RequestFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RequestFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public void createRequest(String jSonStr, String title) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Request(new Date(), jSonStr, title));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void createFirstRequest(String jSonStr, String title) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Request(new Date(), jSonStr, title, 1L));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public MovieAllDTO getSingleByTitle(String title) throws WebApplicationException {
        String jSon = getEntityManager().createQuery("SELECT r.jSon FROM Request r WHERE r.title = :title AND r.movieReqId = 1", String.class)
                .setParameter("title", title)
                .getSingleResult();
        return gson.fromJson(jSon, MovieAllDTO.class);
    }

    public RequestDTO getRequestCountByTitleV2(String title) throws WebApplicationException {
        Long jSon = getEntityManager().createQuery("SELECT COUNT(r.title) FROM Request r WHERE r.title = :title", Long.class)
                .setParameter("title", title)
                .getSingleResult();
        return new RequestDTO(title, jSon);
    }

}
