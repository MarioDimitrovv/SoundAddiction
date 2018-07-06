package com.soundaddiction.model;

import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.util.BCrypt;
import com.soundaddiction.util.Checker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private int userId;
    private int isAdmin; // 1 means Admin
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private double money;
    private List<Song> songs = new ArrayList<Song>();

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

    public void hashUsersPassword(){
        if(Checker.isValidPassword(this.password)){
            this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
        }
    }

    public void addSong(Song song){
        if(song != null){
            this.songs.add(song);
        }
    }

    private void setUserId(int userId) throws InvalidUserDataException {
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
}
