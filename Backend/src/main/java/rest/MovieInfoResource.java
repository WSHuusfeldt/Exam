/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entities.dto.ImdbDTO;
import entities.dto.MetacriticDTO;
import entities.dto.MovieAllDTO;
import entities.dto.MovieSimpleDTO;
import entities.dto.PosterDTO;
import entities.dto.TomatoDTO;
import facades.CriticsFacade;
import facades.MovieSimpleFacade;
import facades.RequestFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author APC
 */
@Path("movieInfo")
public class MovieInfoResource {

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

    //Simple version
    @Path("/findByTitle/{title}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public MovieSimpleDTO getSingleMovie(@PathParam("title") String title) {
        try {
            MovieSimpleDTO msd = facade.fetch(title);
            PosterDTO pDTO = facade.fetchPoster(title);
            msd.setPoster(pDTO.getPoster());
            return msd;
        } catch (NoResultException ex) {
            throw new WebApplicationException("No movie with title: " + title + " was found.", Status.NOT_FOUND);
        }
    }

    @Path("/findByTitleAll/{title}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public MovieAllDTO getSingleMovieAll(@PathParam("title") String title) {
        if (rf.checkForEmptiness(title) != 0) {
            MovieAllDTO m = rf.getSingleByTitle(title);
            rf.createRequest(gson.toJson(m), title);
            return rf.getSingleByTitle(title);
        } else {
            try {
                MovieSimpleDTO msd = facade.fetch(title);
                PosterDTO pDTO = facade.fetchPoster(title);
                MovieAllDTO mad = new MovieAllDTO(msd, pDTO.getPoster(), null, null, null);
                mad.setMetacritic(facadeCritic.fetchMeta(title));
                mad.setTomato(facadeCritic.fetchTomato(title));
                mad.setImdb(facadeCritic.fetchImdb(title));
                String jSon = gson.toJson(mad);
                rf.createFirstRequest(jSon, title);
                return mad;
            } catch (NoResultException ex) {
                throw new WebApplicationException("No movie with title: " + title + " was found.", Status.NOT_FOUND);
            }
        }
    }
    
    public static void main(String[] args) {
        MovieAllDTO m = rf.getSingleByTitle("Die Hard");
        System.out.println(m.getTitle());
    }

}
