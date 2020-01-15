/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import entities.dto.MovieSimpleDTO;
import entities.dto.PosterDTO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author William
 */
public class MovieSimpleFacade {
    
     private static final Gson gson = new Gson();
    private static MovieSimpleFacade instance;
    
    private MovieSimpleFacade() {}
    
    public static MovieSimpleFacade getFacade() {
        if (instance == null) {
            instance = new MovieSimpleFacade();
        }
        return instance;
    }
    
    public MovieSimpleDTO fetch(String name) {
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://exam-1187.mydemos.dk/movieInfo/" + name.trim().replace(" ", "%20"));
            System.out.println(url);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.addRequestProperty("User-Agent", "Mozilla/4.76;Chrome"); 
            String jsonStr = "";
            try ( Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
            return gson.fromJson(jsonStr, MovieSimpleDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }
    }
    
    public PosterDTO fetchPoster(String name) {
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://exam-1187.mydemos.dk/moviePoster/" + name.trim().replace(" ", "%20"));
            System.out.println(url);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.addRequestProperty("User-Agent", "Mozilla/4.76;Chrome"); 
            String jsonStr = "";
            try ( Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
            return gson.fromJson(jsonStr, PosterDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }
    }
    
}
