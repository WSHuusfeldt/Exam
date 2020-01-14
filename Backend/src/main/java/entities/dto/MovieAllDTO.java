/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.dto;

import entities.MovieInfo;

/**
 *
 * @author APC
 */
public class MovieAllDTO {
    
    private Long id;
    private String title;
    private int year;
    private String plot;
    private String directors;
    private String genres;
    private String cast;
    private String poster;
    private MetacriticDTO metacritic;
    private TomatoDTO tomato;
    private ImdbDTO imdb;

    public MovieAllDTO() {
    }

    public MovieAllDTO(Long id, String title, int year, String plot, String directors, String genres, String cast, String poster, MetacriticDTO metacritic, TomatoDTO tomato, ImdbDTO imdb) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.plot = plot;
        this.directors = directors;
        this.genres = genres;
        this.cast = cast;
        this.poster = poster;
        this.metacritic = metacritic;
        this.tomato = tomato;
        this.imdb = imdb;
    }
    
    public MovieAllDTO(MovieInfo movieInfo, String poster, MetacriticDTO mDTO, TomatoDTO tDTO, ImdbDTO iDTO) {
        this.id = movieInfo.getId();
        this.title = movieInfo.getTitle();
        this.year = movieInfo.getYear();
        this.plot = movieInfo.getPlot();
        this.directors = movieInfo.getDirectors();
        this.genres = movieInfo.getGenres();
        this.cast = movieInfo.getCast();
        this.poster = poster;
        this.metacritic = metacritic;
        this.tomato = tomato;
        this.imdb = imdb;
    }
    
    public MovieAllDTO(MovieSimpleDTO movie, String poster, MetacriticDTO mDTO, TomatoDTO tDTO, ImdbDTO iDTO) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.plot = movie.getPlot();
        this.directors = movie.getDirectors();
        this.genres = movie.getGenres();
        this.cast = movie.getCast();
        this.poster = poster;
        this.metacritic = metacritic;
        this.tomato = tomato;
        this.imdb = imdb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public MetacriticDTO getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(MetacriticDTO metacritic) {
        this.metacritic = metacritic;
    }

    public TomatoDTO getTomato() {
        return tomato;
    }

    public void setTomato(TomatoDTO tomato) {
        this.tomato = tomato;
    }

    public ImdbDTO getImdb() {
        return imdb;
    }

    public void setImdb(ImdbDTO imdb) {
        this.imdb = imdb;
    }
    

    
}
