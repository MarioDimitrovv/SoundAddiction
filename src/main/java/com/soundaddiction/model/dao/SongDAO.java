package com.soundaddiction.model.dao;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.Genre;
import com.soundaddiction.model.Song;
import com.soundaddiction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SongDAO {

    @Autowired
    private DBManager dbManager;
    @Autowired
    private GenreDAO genreDAO;
    @Autowired
    private UserDAO userDAO;

    public Song getSongById(int songId) throws SQLException, InvalidSongDataException,
                                                                InvalidUserDataException {

        Song song = null;

        String getSongById = "SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating," +
                             " s.genre_id, s.resource_path, s.price, s.image_path" +
                             " FROM songs AS s WHERE s.song_id = ?;";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(getSongById)){
            ps.setInt(1, songId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {

                    Genre genre = genreDAO.getGenreBySongId(songId);
                    Map<User, Double> raters = userDAO.getRatersBySongId(songId);

                    song = new Song(rs.getInt("song_id"),
                                rs.getString("singer"),
                                rs.getString("album"),
                                rs.getString("name"),
                                rs.getDate("published_date").toLocalDate(),
                                genre,
                                rs.getDouble("rating"),
                                rs.getDouble("price"),
                                rs.getString("resource_path"),
                                rs.getString("image_path"),
                                raters);
                }
            }
        }
        return song;
    }
    public List<Song> getAllSongs() throws SQLException, InvalidSongDataException,
                                                                InvalidUserDataException {

        String getAllSongs = "SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating," +
                " s.genre_id, s.resource_path, s.price, s.image_path" +
                " FROM songs AS s;";

        List<Song> songs = new ArrayList<>();
        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(getAllSongs)) {
            try(ResultSet rs = ps.executeQuery();){
                while(rs.next()) {
                    int songId = rs.getInt("song_id");
                    Genre genre = genreDAO.getGenreBySongId(songId);
                    Map<User, Double> raters = userDAO.getRatersBySongId(songId);
                    Song song = new Song(rs.getInt("song_id"),
                            rs.getString("singer"),
                            rs.getString("album"),
                            rs.getString("name"),
                            rs.getDate("published_date").toLocalDate(),
                            genre,
                            rs.getDouble("rating"),
                            rs.getDouble("price"),
                            rs.getString("resource_path"),
                            rs.getString("image_path"),
                            raters);
                    songs.add(song);
                }
            }
        }
        return songs;
    }

    public List<Song> getSongsByUserId(int userId) throws SQLException, InvalidSongDataException,
                                                                InvalidUserDataException {

        String searchSongsOfAUser = "SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating," +
                                    "s.genre_id, s.resource_path, s.price, s.image_path" +
                                    " FROM songs AS s" +
                                    " JOIN user_has_songs AS uhs" +
                                    " ON uhs.song_id = s.song_id" +
                                    " WHERE uhs.user_id = ?;";

        List<Song> songs = new ArrayList<>();

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(searchSongsOfAUser)){
            ps.setInt(1, userId);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    int songId = rs.getInt("song_id");
                    Genre genre = genreDAO.getGenreBySongId(songId);
                    Map<User, Double> raters = userDAO.getRatersBySongId(songId);
                    Song song = new Song(rs.getInt("song_id"),
                                        rs.getString("singer"),
                                        rs.getString("album"),
                                        rs.getString("name"),
                                        rs.getDate("published_date").toLocalDate(),
                                        genre,
                                        rs.getDouble("rating"),
                                        rs.getDouble("price"),
                                        rs.getString("resource_path"),
                                        rs.getString("image_path"),
                                        raters);
                    songs.add(song);
                }
            }
        }
        return songs;
    }

    public List<Song> getSongsByName(String substring) throws SQLException, InvalidSongDataException,
                                                                InvalidUserDataException {

        String searchByName = "SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating," +
                                " s.genre_id, s.resource_path, s.price, s.image_path" +
                                " FROM songs AS s" +
                                " WHERE s.name LIKE ?" +
                                " ORDER BY s.name ASC;";

        List<Song> songs = new ArrayList<>();
        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(searchByName)) {
            ps.setString(1, '%'+substring+'%');
                try(ResultSet rs = ps.executeQuery();){
                    while(rs.next()) {
                        int songId = rs.getInt("song_id");
                        Genre genre = genreDAO.getGenreBySongId(songId);
                        Map<User, Double> raters = userDAO.getRatersBySongId(songId);
                        Song song = new Song(rs.getInt("song_id"),
                                                rs.getString("singer"),
                                                rs.getString("album"),
                                                rs.getString("name"),
                                                rs.getDate("published_date").toLocalDate(),
                                                genre,
                                                rs.getDouble("rating"),
                                                rs.getDouble("price"),
                                                rs.getString("resource_path"),
                                                rs.getString("image_path"),
                                                raters);
                        songs.add(song);
                    }
                }
        }
        return songs;
    }

    public List<Song> getSongsBySingerName(String singer) throws SQLException, InvalidSongDataException,
                                                                InvalidUserDataException {

        String searchBySinger = "SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating," +
                                  " s.genre_id, s.resource_path, s.price, s.image_path" +
                                  " FROM songs AS s" +
                                  " WHERE s.singer LIKE ?" +
                                  " ORDER BY s.name ASC;";

        List<Song> songs = new ArrayList<>();

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(searchBySinger))
        {
            ps.setString(1, '%'+singer+'%');
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    int songId = rs.getInt("song_id");
                    Genre genre = genreDAO.getGenreBySongId(songId);
                    Map<User, Double> raters = userDAO.getRatersBySongId(songId);

                    Song song = new Song(rs.getInt("song_id"),
                            rs.getString("singer"),
                            rs.getString("album"),
                            rs.getString("name"),
                            rs.getDate("published_date").toLocalDate(),
                            genre,
                            rs.getDouble("rating"),
                            rs.getDouble("price"),
                            rs.getString("resource_path"),
                            rs.getString("image_path"),
                            raters);
                    songs.add(song);
                }
            }
        }

        return songs;
    }

    public List<Song> getSongsByGenre(Genre genre) throws SQLException, InvalidSongDataException,
                                                                InvalidUserDataException {

        String searchByGenre = "SELECT s.song_id, s.name, s.singer, s.album, s.published_date, s.rating," +
                                " s.genre_id, s.resource_path, s.price, s.image_path" +
                                " FROM songs AS s JOIN genres AS g" +
                                " ON s.genre_id = g.genre_id" +
                                " WHERE g.value = ?" +
                                " ORDER BY s.name ASC;";

        List<Song> songs = new ArrayList<>();

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(searchByGenre))
        {
            ps.setString(1, genre.getValue());

            try(ResultSet rs = ps.executeQuery())
            {
                while(rs.next())
                {
                    int songId = rs.getInt("song_id");
                    Map<User, Double> raters = userDAO.getRatersBySongId(songId);

                    Song song = new Song(rs.getInt("song_id"),
                            rs.getString("singer"),
                            rs.getString("album"),
                            rs.getString("name"),
                            rs.getDate("published_date").toLocalDate(),
                            genre,
                            rs.getDouble("rating"),
                            rs.getDouble("price"),
                            rs.getString("resource_path"),
                            rs.getString("image_path"),
                            raters);
                    songs.add(song);
                }
            }
        }
        return songs;
    }

    public void saveSong(Song song) throws SQLException, InvalidSongDataException {

        String saveSong = "INSERT INTO songs(name, singer, album, published_date, genre_id," +
                            " resource_path, price, image_path) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(saveSong,
                                                PreparedStatement.RETURN_GENERATED_KEYS)){

            //Set values to the parameters to save the song in DB
            ps.setString(1, song.getName());
            ps.setString(2, song.getSinger());
            ps.setString(3, song.getAlbum());
            ps.setDate(4, Date.valueOf(song.getPublishDate()));
            ps.setInt(5, song.getGenre().getGenreId());
            ps.setString(6, song.getResourcePath());
            ps.setDouble(7, song.getPrice());
            ps.setString(8, song.getImagePath());

            //If song was successfully saved -> get its ID and set it to the object
            if(ps.executeUpdate() > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()) {
                        song.setSongId(rs.getInt("GENERATED_KEY"));
                    }
                }
            }
        }

    }

    public void deleteSong(Song song) throws SQLException {

        String deleteSong = "DELETE FROM songs WHERE song_id = ?;";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(deleteSong)){
            ps.setInt(1,song.getSongId());
            ps.executeUpdate();
        }
    }

    public void updateSong(Song song) throws SQLException {

        String updateSong = "UPDATE songs SET " +
                            "name = ?," +
                            "singer = ?," +
                            "album = ?," +
                            "published_date = ?," +
                            "genre_id = ?," +
                            "resource_path = ?," +
                            "price = ?" +
                            "image_path = ?," +
                            " WHERE song_id = ?;";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(updateSong)){
            ps.setString(1, song.getName());
            ps.setString(2, song.getSinger());
            ps.setString(3, song.getAlbum());
            ps.setDate(4, Date.valueOf(song.getPublishDate()));
            ps.setInt(5, song.getGenre().getGenreId());
            ps.setString(6, song.getResourcePath());
            ps.setDouble(7, song.getPrice());
            ps.setString(6, song.getImagePath());
            ps.setInt(8, song.getSongId());
            ps.executeUpdate();

            System.out.println("Successfully updated song: "+song.getName());
        }
    }

}
