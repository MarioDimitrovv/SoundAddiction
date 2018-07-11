package com.soundaddiction.controller;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserActivityException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.manager.UserManager;
import com.soundaddiction.model.Song;
import com.soundaddiction.model.User;
import com.soundaddiction.model.dao.SongDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
@RequestMapping(value ="/auth")
@Component
public class UserInteractionsController {

    private static final double MAX_RATING = 10d;
    private static final double MIN_RATING = 1d;
    private static final int MAX_REVIEW_CHARS = 480;
    private static final int MIN_REVIEW_CHARS = 3;

    @Autowired
    private SongDAO songDAO;
    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/buySong", method = RequestMethod.POST)
    public ResponseEntity<Boolean> buySong(HttpSession session, @RequestParam("songId") Integer songId){
        try {
            // Get user from session
            User user = (User) session.getAttribute("USER");
            // Get song from database
            Song song = songDAO.getSongById(songId);

            // Check if the songId is valid
            if (song == null) {
                // If not --> return an HTTP code for no such product (400);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Add song to bought songs
            boolean isSongAlreadyBought = userManager.buySong(user, song);

            System.out.println("\nSuccessfully bought song: "+song.getName());

            //Return the result and an OK status
            return new ResponseEntity<Boolean>(isSongAlreadyBought, HttpStatus.OK);
        }
        catch (SQLException | InvalidUserActivityException | InvalidUserDataException | InvalidSongDataException e) {
            //Return an entity with a status code for Internal Server Error (handling is done via JS)
            System.out.println("Error buying song "+e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/rateSong", method = RequestMethod.POST)
    public ResponseEntity<Boolean> addProductToCart(HttpSession session,
                                                    @RequestParam("songId") Integer songId,
                                                    @RequestParam("rating") Double rating){
        try {
            // Get user from session
            User user = (User) session.getAttribute("USER");

            // Get song from database
            Song song = songDAO.getSongById(songId);

            // Check if the songId and rating are valid
            if (song == null || rating > MAX_RATING || rating < MIN_RATING) {
                // If not --> return an HTTP code for no such product (400);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Rate song
            userManager.rateSong(user, song, rating);
            //Return an OK status
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (SQLException | InvalidSongDataException | InvalidUserDataException | InvalidUserActivityException e) {
            //Return an entity with a status code for Internal Server Error (handling is done via JS)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
