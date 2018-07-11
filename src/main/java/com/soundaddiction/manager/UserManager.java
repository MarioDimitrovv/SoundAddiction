package com.soundaddiction.manager;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserActivityException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.Song;
import com.soundaddiction.model.User;
import com.soundaddiction.model.dao.SongDAO;
import com.soundaddiction.model.dao.UserActivitiesDAO;
import com.soundaddiction.model.dao.UserDAO;
import com.soundaddiction.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class UserManager {

    private static final double MAX_RATING = 10d;
    private static final double MIN_RATING = 1d;

    @Autowired
    private UserActivitiesDAO userActivitiesDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SongDAO songDAO;

    //Activities of a regular user
    public boolean buySong(User user, Song song) throws SQLException, InvalidUserActivityException, InvalidUserDataException {
        if(song != null && user != null){
            //If the user already has this song return false
            if(user.addSong(song)){
                //Decrease user's money
                user.setMoney(user.getMoney() - song.getPrice());
                userDAO.updateUser(user);
                userActivitiesDAO.buyASong(song,user);
                return true;
            }
            return false;
        }
        throw new InvalidUserActivityException("Problem with buying a song!");
    }

    public void rateSong(User user, Song song, double rating) throws SQLException, InvalidUserActivityException {
        if(song != null && user != null && rating >= MIN_RATING && rating <= MAX_RATING){
            userActivitiesDAO.rateASong(song, user, rating);
            song.addRater(user, rating);
            return;
        }
        throw new InvalidUserActivityException("Problem adding a rating to a song!");
    }

    public void commentSong(User user, Song song, String content) throws SQLException, InvalidUserActivityException {
        if(song != null && user != null && Checker.isNotNullOrEmpty(content)){
            userActivitiesDAO.commentASong(song, user, content);
            return;
        }
        throw new InvalidUserActivityException("Problem with adding a comment to a song!");
    }

    public void deleteAccount(User user) throws SQLException, InvalidUserActivityException {
        if(user != null){
            if(!userDAO.deleteUser(user)){
                throw new InvalidUserActivityException("Unsuccessfully deleting user's account!");
            }
        }
    }

    //Activities of an Admin
    public void saveSongToDB(User user, Song song) throws InvalidUserActivityException,
            InvalidSongDataException,
            SQLException {
        if(song != null && user != null && user.getIsAdmin() == 1){
            songDAO.saveSong(song);
            return;
        }
        throw new InvalidUserActivityException("Problem with saving the song to the Database!");
    }

    public void updateSong(User user, Song song) throws InvalidUserActivityException, SQLException {
        if(song != null&& user != null && user.getIsAdmin() == 1){
            songDAO.updateSong(song);
            return;
        }
        throw new InvalidUserActivityException("Problem with updating the song in the Database!");
    }

    public void deleteSong(User user, Song song) throws InvalidUserActivityException, SQLException {
        if(song != null && user != null && user.getIsAdmin() == 1){
            songDAO.deleteSong(song);
            return;
        }
        throw new InvalidUserActivityException("Problem with deleting the song from the Database!");
    }

}
