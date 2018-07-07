package com.soundaddiction.model;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserActivityException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.dao.SongDAO;
import com.soundaddiction.model.dao.UserActivitiesDAO;
import com.soundaddiction.model.dao.UserDAO;
import com.soundaddiction.util.BCrypt;
import com.soundaddiction.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User {

    @Autowired
    private UserActivitiesDAO userActivitiesDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SongDAO songDAO;

    //Fields
    private int userId;
    private int isAdmin; // 1 means Admin
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private double money;
    private List<Song> songs = new ArrayList<Song>();

    //Constructors
    public User(int userId,
                int isAdmin,
                String email,
                String password,
                String firstName,
                String lastName,
                double money,
                List<Song> songs) throws InvalidUserDataException {

        this.setUserId(userId);
        this.setIsAdmin(isAdmin);
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMoney(money);
        this.setSongs(songs);

    }

    public User(String email,
                String password,
                String firstName,
                String lastName) throws InvalidUserDataException{

        this.setEmail(email);
        this.setPassword(password);
        this.hashUsersPassword();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setIsAdmin(0); // 0 -> Not an Admin
    }

    //Activities of a regular user
    public void buySong(Song song) throws SQLException, InvalidUserActivityException {
        if(song != null){
            userActivitiesDAO.buyASong(song,this);
            this.songs.add(song);
            return;
        }
        throw new InvalidUserActivityException("Problem with buying a song!");
    }

    public void rateSong(Song song, double rating) throws SQLException, InvalidUserActivityException {
        if(song != null && rating > 0 && rating < 5){
            userActivitiesDAO.rateASong(song, this, rating);
            song.addRater(this, rating);
            return;
        }
        throw new InvalidUserActivityException("Problem adding a rating to a song!");
    }

    public void commentSong(Song song, String content) throws SQLException, InvalidUserActivityException {
        if(song != null && Checker.isNotNullOrEmpty(content)){
            userActivitiesDAO.commentASong(song, this, content);
            return;
        }
        throw new InvalidUserActivityException("Problem with adding a comment to a song!");
    }

    public void deleteMyAccount() throws SQLException, InvalidUserActivityException {
        if(!userDAO.deleteUser(this)){
           throw new InvalidUserActivityException("Unsuccessfully deleting user's account!");
        }
    }

    public void hashUsersPassword(){
        if(Checker.isValidPassword(this.password)){
            this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
        }
    }

    //Activities of an Admin
    public void saveSongToDB(Song song) throws InvalidUserActivityException,
                                               InvalidSongDataException,
                                               SQLException {
        if(song != null && this.isAdmin == 1){
            songDAO.saveSong(song);
            return;
        }
        throw new InvalidUserActivityException("Problem with saving the song to the Database!");
    }

    public void updateSong(Song song) throws InvalidUserActivityException, SQLException {
        if(song != null && this.isAdmin == 1){
            songDAO.updateSong(song);
            return;
        }
        throw new InvalidUserActivityException("Problem with updating the song in the Database!");
    }

    public void deleteSong(Song song) throws InvalidUserActivityException, SQLException {
        if(song != null && this.isAdmin == 1){
            songDAO.deleteSong(song);
            return;
        }
        throw new InvalidUserActivityException("Problem with deleting the song from the Database!");
    }

    //Setters
    public void setUserId(int userId) throws InvalidUserDataException {
        if(userId > 0){
            this.userId = userId;
            return;
        }
        throw new InvalidUserDataException("Invalid user id!");
    }

    public void setIsAdmin(int isAdmin) throws InvalidUserDataException {
        if(isAdmin == 0 || isAdmin == 1) {
            this.isAdmin = isAdmin;
            return;
        }
        throw new InvalidUserDataException("User field isAdmin should be 0 or 1!");
    }

    public void setEmail(String email) throws InvalidUserDataException {
        if(Checker.isValidEmail(email)) {
            this.email = email;
            return;
        }
        throw new InvalidUserDataException("Problem setting user's email!");
    }

    public void setPassword(String password) throws InvalidUserDataException {
        if(Checker.isValidPassword(password)) {
            this.password = password;
            return;
        }
        throw new InvalidUserDataException("Invalid user password!");
    }

    public void setFirstName(String firstName) throws InvalidUserDataException {
        if(Checker.isValidName(firstName)) {
            this.firstName = firstName;
        }
        throw new InvalidUserDataException("Problem setting user's first name!");
    }

    public void setLastName(String lastName) throws InvalidUserDataException {
        if(Checker.isValidName(lastName)) {
            this.lastName = lastName;
        }
        throw new InvalidUserDataException("Problem setting user's last name!");
    }

    public void setMoney(double money) throws InvalidUserDataException {
        if(money >= 0){
            this.money = money;
        }
        throw new InvalidUserDataException("Invalid users's amount of money setting!");
    }

    public void setSongs(List<Song> songs) throws InvalidUserDataException {
        if(songs != null) {
            this.songs = songs;
        }
        throw new InvalidUserDataException("Problem setting user's songs");
    }

    //Getters
    public int getUserId() {
        return userId;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getMoney() {
        return money;
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    //HashCode and equals for Map<>raters in SongPOJO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, email);
    }
}
