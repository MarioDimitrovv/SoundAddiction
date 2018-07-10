package com.soundaddiction.model.dao;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.Song;
import com.soundaddiction.model.User;
import org.mindrot.jbcrypt.BCrypt;
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

    public User getUserById(int userId) throws SQLException,
                                        InvalidSongDataException, InvalidUserDataException {

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
                if(rs.next()) {

                    //First get user's songs
                    List<Song> songs = new ArrayList<>(songDAO.getSongsByUserId(userId));

                    //Create object user with the given userId
                    user = new User(rs.getInt("user_id"),
                            rs.getInt("is_admin"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDouble("money"),
                            songs);
                }
            }
        }
        return user;
    }

    public User getUserByEmail(String email, String password) throws SQLException,
                                            InvalidUserDataException, InvalidSongDataException {

        User user = null;

        String getUserByEmail = "SELECT u.user_id, u.is_admin, u.email, u.password," +
                " u.first_name, u.last_name, u.money " +
                "FROM users AS u " +
                "WHERE u.email = ?;";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(getUserByEmail)){
            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {

                    int userId = rs.getInt("user_id");
                    String hashedPasswordFromDB = rs.getString("password");

                    //If entered password and hashed password in the database match
                    if(BCrypt.checkpw(password, hashedPasswordFromDB)) {
                        //Create object user with the given userId
                        user = this.getUserById(userId);
                    }
                    else{
                        throw new SQLException("Passwords do not match!");
                    }
                }else{
                    throw new SQLException("There is no user with this email!");
                }
            }
        }
        return user;
    }

    public void registerUser(User user) throws SQLException, InvalidUserDataException {

        String insertUserInDB = "INSERT INTO users(email, password, first_name, last_name)" +
                                " VALUES(?, ?, ?, ?);";

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(insertUserInDB,
                                                    PreparedStatement.RETURN_GENERATED_KEYS)){

            //Set values to the parameters to save the user in DB
            ps.setString(1, user.getEmail());
            ps.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());

            //If user was successfully saved -> get his ID and set it to the object
            if(ps.executeUpdate() > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next()) {
                        int userId = rs.getInt("GENERATED_KEY");
                        user.setUserId(userId);
                        user.setIsAdmin(0);
                        user.setMoney(100);
                    }
                }
            }
        }
    }

    public boolean deleteUser(User user) throws SQLException {
        String deleteUserById = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement ps = dbManager.getConnection().prepareStatement(deleteUserById)) {
            ps.setInt(1, user.getUserId());
            ps.executeUpdate();

            System.out.println("Successfully deleted account from the database!");
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public Map<User, Double> getRatersBySongId(int songId) throws SQLException, InvalidUserDataException,
                                                                            InvalidSongDataException {

        Map<User, Double> raters = new HashMap<>();

        String ratersBySongId = "SELECT u.user_id, u.is_admin, u.email, u.password," +
                                " u.first_name, u.last_name, " +
                                "u.money, shr.rating  " +
                                "FROM users AS u " +
                                "JOIN song_has_raters AS shr " +
                                "ON shr.user_id = u.user_id " +
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
