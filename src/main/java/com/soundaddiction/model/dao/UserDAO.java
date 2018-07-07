package com.soundaddiction.model.dao;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.Song;
import com.soundaddiction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {

    @Autowired
    private DBManager dbManager;

    @Autowired
    private SongDAO songDAO;

    public User getUserById(int userId) throws SQLException, InvalidSongDataException, InvalidUserDataException {

        User user = null;
        String getUserByIdQuery = "SELECT u.user_id, u.is_admin, u.email, u.password, u.first_name," +
                                   " u.last_name, u.money " +
                                   "FROM users AS u WHERE u.user_id = ?;";

        //Create prepared statement
        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(getUserByIdQuery)){
            //Set parameter to the searched one from the query
            ps.setInt(1, userId);

            //Execute given query from prepared statement
            try(ResultSet rs = ps.executeQuery()){

                //Moves the resultSet to the first row
                rs.next();

                //First get user's songs
                List<Song> songs = new ArrayList<>(songDAO.getSongsByUserId(userId));

                //Create object user with the given userId
                user = new User(rs.getInt("user_id"),
                                rs.getInt("is_admin"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getDouble("money"),
                                songs);
            }
        }
        return user;
    }

    public User getUserByEmailAndPass(String email, String password) throws SQLException, InvalidUserDataException {

        User user = null;

        String getUserByEmailAndPass = "SELECT u.user_id, u.is_admin, u.email, u.password," +
                " u.first_name, u.last_name, u.money " +
                "FROM users AS u " +
                "WHERE u.email = ? AND u.password = ?;";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(getUserByEmailAndPass)){
            ps.setString(1, email);
            ps.setString(2, password);

            try(ResultSet rs = ps.executeQuery()){
                rs.next();

                int userId = rs.getInt("user_id");

                //First get user's songs
                List<Song> songs = new ArrayList<>(songDAO.getSongsByUserId(userId));

                //Create object user with the given userId
                user = new User(userId,
                                rs.getInt("is_admin"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getDouble("money"),
                                songs);
            }
        }
        return user;
    }

    public void registerUser(User user) throws SQLException, InvalidUserDataException {

        String insertUserInDB = "INSERT INTO users(email, password, first_name, last_name)" +
                                "VALUES(?, ?, ?, ?);";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(insertUserInDB,
                                                    PreparedStatement.RETURN_GENERATED_KEYS)){

            //Set values to the parameters to save the user in DB
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());

            //If user was successfully saved -> get his ID and set it to the object
            if(ps.executeUpdate() > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    rs.next();
                    int userId = rs.getInt("user_id");
                    user.setUserId(userId);
                }
            }

        }

    }

    public void deleteUser(User user) throws SQLException {
        String deleteUserById = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement ps = dbManager.getConnection().prepareStatement(deleteUserById)) {
            ps.setInt(1, user.getUserId());
            ps.executeUpdate();
        }
        System.out.println("Successfully deleted account from the database.");
    }

    public Map<User, Double> getRatersBySongId(int songId) throws SQLException, InvalidUserDataException,
                                                                            InvalidSongDataException {

        Map<User, Double> raters = new HashMap<>();

        String ratersBySongId = "SELECT u.user_id, u.is_admin, u.email, u.password, u.first_name, u.last_name, " +
                                "u.money, shr.rating  " +
                                "FROM users AS u" +
                                "JOIN song_has_raters AS shr" +
                                "ON shr.user_id = u.user_id" +
                                "WHERE shr.song_id = ?; ";
        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(ratersBySongId)){
            ps.setInt(1, songId);

            try(ResultSet rs = ps.executeQuery()){

                while(rs.next()){

                    //First get the rating and the id of the user
                    double rating = rs.getDouble("rating");
                    int userId = rs.getInt("user_id");

                    User user = this.getUserById(userId);
                    raters.put(user, rating);

                }
            }
        }
        return raters;
    }
}
