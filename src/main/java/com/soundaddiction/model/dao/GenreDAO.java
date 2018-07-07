package com.soundaddiction.model.dao;

import com.soundaddiction.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class GenreDAO {

    @Autowired
    private DBManager dbManager;

    public Genre getGenreBySongId(int songId) throws SQLException {

        String getGenreOfASong = "SELECT g.genre_id, g.value FROM genres AS g" +
                                 "JOIN songs AS s" +
                                 "ON s.genre_id = g.genre_id" +
                                 "WHERE s.song_id = ?;";

        Genre genre = null;

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(getGenreOfASong)){
            ps.setInt(1, songId);
            ps.executeQuery();

        }

        return null;
    }
}
