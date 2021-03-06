/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import entities.dto.ImdbDTO;
import entities.dto.MetacriticDTO;
import entities.dto.TomatoDTO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author William
 */
public class CriticsFacade {
    
    private static final Gson gson = new Gson();
    private static CriticsFacade instance;
    
    private CriticsFacade() {}
    
    public static CriticsFacade getFacade() {
        if (instance == null) {
            instance = new CriticsFacade();
        }
        return instance;
    }
    
    
    public MetacriticDTO fetchMeta(String name) {
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://exam-1187.mydemos.dk/metacriticScore/" + name.trim().replace(" ", "%20"));
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
            return gson.fromJson(jsonStr, MetacriticDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }
    }
    
    public TomatoDTO fetchTomato(String name) {
        HttpURLConnection con = null;
        try {
            URL urlViewer = new URL("http://exam-1187.mydemos.dk/tomatoesScore/" + name.trim().replace(" ", "%20"));
            System.out.println(urlViewer);
            con = (HttpURLConnection) urlViewer.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.addRequestProperty("User-Agent", "Mozilla/4.76;Chrome"); 
            String jsonStr = "";
            try ( Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
            return gson.fromJson(jsonStr, TomatoDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }
    }
    
    public ImdbDTO fetchImdb(String name) {
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://exam-1187.mydemos.dk/imdbScore/" + name.trim().replace(" ", "%20"));
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
            return gson.fromJson(jsonStr, ImdbDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }
    }
    
}
