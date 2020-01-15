/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Request;
import entities.Role;
import entities.User;
import entities.dto.RequestDTO;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
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
import utils.EMF_Creator;

/**
 *
 * @author APC
 */
public class CountResourceTest {
    
   private static EntityManagerFactory emf;
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    User user;
    Request request, request2;

    // Setup the DataBase in a known state BEFORE EACH TEST
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

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        httpServer.shutdownNow();
    }

    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }
    
    

    /**
     * Test of getJson method, of class CountResource.
     */
    @Test
    public void testGetJson() {
        
    }

    /**
     * Test of getRequestCountByTitle method, of class CountResource.
     */
    @Test
    public void testGetRequestCountByTitle200() {
        login("user", "test1");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken).when()
                .get("/movieCount/" + request2.getTitle()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(2));
    }
    
    @Test
    public void testGetRequestCountByTitle403() {
        given()
                .contentType("application/json")
                .get("/movieCount/" + request2.getTitle()).then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN_403.getStatusCode());
    }
    
}
