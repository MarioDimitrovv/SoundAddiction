package com.soundaddiction.model;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.util.Checker;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Song {

    private int songId;
    private String singer;
    private String album;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    private Genre genre;
    private double rating;
    private double price;
    private String resourcePath;
    private Map<User, Double> raters = new HashMap<>();

    public Song(int songId,
                String singer,
                String album,
                String name,
                LocalDate publishDate,
                Genre genre,
                double rating,
                double price,
                String resourcePath,
                Map<User,Double> raters) throws InvalidSongDataException {

        this(singer, album, name, publishDate, genre, price, resourcePath);
        this.setSongId(songId);
        this.setRating(rating);
        this.setRaters(raters);

    }

    public Song(String singer,
                String album,
                String name,
                LocalDate publishDate,
                Genre genre,
                double price,
                String resourcePath) throws InvalidSongDataException{

        this.setSinger(singer);
        this.setAlbum(album);
        this.setName(name);
        this.setPublishDate(publishDate);
        this.setGenre(genre);
        this.setPrice(price);
        this.setResourcePath(resourcePath);
    }

    public void addRater(User rater, double rating){
        if(rater != null && rating > 0 && rating <= 5){
            this.raters.put(rater, rating);
        }
    }

    public void setSongId(int songId) throws InvalidSongDataException {
        if(songId > 0) {
            this.songId = songId;
            return;
        }
        throw new InvalidSongDataException("Invalid setting of song id!");
    }

    public void setSinger(String singer) throws InvalidSongDataException {
        if(Checker.isNotNullOrEmpty(singer)) {
            this.singer = singer;
            return;
        }
        throw new InvalidSongDataException("Invalid song's singer!");
    }

    public void setAlbum(String album) throws InvalidSongDataException {
        if(Checker.isNotNullOrEmpty(album)) {
            this.album = album;
            return;
        }
        throw new InvalidSongDataException("Invalid song's album!");
    }

    private void setName(String name) throws InvalidSongDataException {
        if(Checker.isNotNullOrEmpty(name)) {
            this.name = name;
            return;
        }
        throw new InvalidSongDataException("Invalid song's name!");
    }

    public void setPublishDate(LocalDate publishDate) throws InvalidSongDataException {
        if(Checker.isValidDate(publishDate)) {
            this.publishDate = publishDate;
            return;
        }
        throw new InvalidSongDataException("Invalid song's publish date!");
    }

    public void setGenre(Genre genre) throws InvalidSongDataException {
        if(genre != null) {
            this.genre = genre;
            return;
        }
        throw new InvalidSongDataException("Invalid song's genre!");
    }

    public void setRating(double rating) throws InvalidSongDataException {
        if(rating >= 1 && rating <= 5) {
            this.rating = rating;
            return;
        }
        throw new InvalidSongDataException("Invalid song's rating!");
    }

    public void setPrice(double price) throws InvalidSongDataException {
        if(price >= 0) {
            this.price = price;
            return;
        }
        throw new InvalidSongDataException("Invalid song's price!");
    }

    public void setResourcePath(String resourcePath) throws InvalidSongDataException {
        if(Checker.isNotNullOrEmpty(resourcePath)) {
            this.resourcePath = resourcePath;
            return;
        }
        throw new InvalidSongDataException("Invalid song's resource path!");
    }

    public void setRaters(Map<User, Double> raters) throws InvalidSongDataException {
        if(raters != null) {
            this.raters = raters;
            return;
        }
        throw new InvalidSongDataException("Problem setting song's raters!");
    }

    public int getSongId() {
        return songId;
    }

    public String getSinger() {
        return singer;
    }

    public String getAlbum() {
        return album;
    }

    public String getName() {
        return name;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public Map<User, Double> getRaters() {
        return Collections.unmodifiableMap(raters);
    }
}
