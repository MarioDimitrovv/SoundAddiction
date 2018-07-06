package com.soundaddiction.model.dao;

import com.soundaddiction.model.Genre;
import com.soundaddiction.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SongDAO {

    @Autowired
    private DBManager dbManager;

    public Song getSongById(int songId) {

        List<Song> songs = new ArrayList<>();

        return null;
    }

    public List<Song> getSongsByUserId(int userId) {

        List<Song> songs = new ArrayList<>();

        return null;
    }

    public List<Song> getSongsBySubstring(String substring) {

        List<Song> songs = new ArrayList<>();

        return null;
    }

    public List<Song> getSongsBySingerName(String singer) {

        List<Song> songs = new ArrayList<>();

        return null;
    }

    public List<Song> getSongsByGenre(Genre genre) {

        List<Song> songs = new ArrayList<>();

        return null;
    }

    public void saveSong(Song song) {

    }

    public void deleteSong(Song song){

    }

    public void updateSong(Song song){

    }

}
