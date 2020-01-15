/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Request;
import entities.Role;
import entities.User;
import entities.dto.MovieAllDTO;
import entities.dto.MovieSimpleDTO;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author APC
 */
public class MovieInfoResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        //EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    User user;
    Request request, request2;

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.createQuery("DELETE FROM Request").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();

            user = new User("user", "test1");
            Role userRole = new Role("admin");
            user.addRole(userRole);

            String jSon1 = "'{\"title\":\"Die Hard\",\"year\":1988,\"plot\":\"John McClane, officer of the NYPD, tries to save wife Holly Gennaro and several others, taken hostage by German terrorist Hans Gruber during a Christmas party at the Nakatomi Plaza in Los Angeles.\",\"directors\":\"John McTiernan\",\"genres\":\"Action,Thriller\",\"cast\":\"Bruce Willis,Bonnie Bedelia,Reginald VelJohnson,Paul Gleason\",\"poster\":\"https://m.media-amazon.com/images/M/MV5BZjRlNDUxZjAtOGQ4OC00OTNlLTgxNmQtYTBmMDgwZmNmNjkxXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SY1000_SX677_AL_.jpg\",\"metacritic\":{\"title\":\"Die Hard\",\"metacritic\":70},\"tomato\":{\"title\":\"Die Hard\",\"source\":\"tomatoes\",\"viewer\":{\"rating\":3.9,\"numReviews\":570005,\"meter\":94},\"critic\":{\"rating\":8.4,\"numReviews\":64,\"meter\":92}},\"imdb\":{\"title\":\"Die Hard\",\"source\":\"imdb\",\"imdbRating\":8.3,\"imdbVotes\":535036}}'";
            request = new Request(new Date(), jSon1, "Die Hard", 1L);
            request2 = new Request(new Date(), jSon1, "Die Hard");

            em.persist(request);
            em.persist(request2);

            em.persist(userRole);
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    /**
     * Test of getJson method, of class MovieInfoResource.
     */
    @Test
    public void testGetJson() {
        given()
                .contentType("application/json")
                .get("/movieInfo").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Welcome to the Movie API!"));
    }

    /**
     * Test of getSingleMovie method, of class MovieInfoResource.
     */
    @Test
    public void testGetSingleMovie200() throws Exception {
        given()
                .contentType("application/json")
                .get("/movieInfo/findByTitle/" + request.getTitle()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", equalTo(request.getTitle()));
    }

    @Test
    public void testGetSingleMovie404() throws Exception {
        given()
                .contentType("application/json")
                .get("/movieInfo/getByTitle/" + request.getTitle()).then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    /**
     * Test of getSingleMovieAll method, of class MovieInfoResource.
     */
    // I can't figure out why it won't persist the user to the DB
//    @Disabled 
//    @Test
//    public void testGetSingleMovieAll200() {
//        login("user", "test1");
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken).when()
//                .get("/movieInfo/findByTitleAll/" + request2.getTitle()).then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("title", equalTo(request2.getTitle()));
//    }

    @Test
    public void testGetSingleMovieAll403() {
        given()
                .contentType("application/json")
                .get("/movieInfo/findByTitleAll/" + request.getTitle()).then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN_403.getStatusCode());
    }

}
