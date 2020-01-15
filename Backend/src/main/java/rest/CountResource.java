/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entities.dto.RequestDTO;
import facades.CriticsFacade;
import facades.MovieSimpleFacade;
import facades.RequestFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author William
 */
@Path("movieCount")
public class CountResource {

    private static final EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/exam",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    @Context
    private UriInfo context;

    private static final MovieSimpleFacade facade = MovieSimpleFacade.getFacade();
    private static final CriticsFacade facadeCritic = CriticsFacade.getFacade();
    private static final RequestFacade rf = RequestFacade.getFacade(emf);
    private static final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"msg\": \"Welcome to the Movie API!\"}";
    }

    @Path("/{title}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public RequestDTO getRequestCountByTitle(@PathParam("title") String title) {
        try {
            return rf.getRequestCountByTitleV2(title);
        } catch (NoResultException ex) {
            throw new WebApplicationException("Movie/request not allowed");
        }
    }

}
