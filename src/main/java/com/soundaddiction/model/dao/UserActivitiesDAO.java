package com.soundaddiction.model.dao;

import com.soundaddiction.model.Song;
import com.soundaddiction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class UserActivitiesDAO {

    @Autowired
    private DBManager dbManager;

    public void buyASong(Song song, User user) throws SQLException {

        String buySong = "INSERT INTO user_has_songs(song_id, user_id)" +
                         "VALUES(?, ?);";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(buySong)){
            ps.setInt(1, song.getSongId());
            ps.setInt(2, user.getUserId());
            ps.executeUpdate();

            System.out.println("Successfully bought song "+song.getName()+
                                " from "+user.getFirstName()+" "+user.getLastName());
        }
    }

    public void rateASong(Song song, User user, double rating) throws SQLException {

        String rateSong = "INSERT INTO song_has_raters(song_id, user_id, rating) " +
                          "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE rating = ?;";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(rateSong)){
            ps.setInt(1, song.getSongId());
            ps.setInt(2, user.getUserId());
            ps.setDouble(3, rating);
            ps.setDouble(4, rating);
            ps.executeUpdate();

            System.out.println("Successfully rated song "+song.getName()+
                                " from "+user.getFirstName()+" "+user.getLastName()+" with rate = "+rating);
        }

    }

    public void commentASong(Song song, User user, String content) throws SQLException {

        String commentSong = "INSERT INTO song_has_comments(user_id, song_id, content, date_time) " +
                             "VALUES(?, ?, ?, now());";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(commentSong)){
            ps.setInt(1, user.getUserId());
            ps.setInt(2, song.getSongId());
            ps.setString(3, content);
            ps.executeUpdate();

            System.out.println("Successfully commented song "+song.getName()+
                    " from "+user.getFirstName()+" "+user.getLastName()+" with content = \n"+content);
        }
    }
}
