package com.soundaddiction.controller;

import com.soundaddiction.model.Song;
import com.soundaddiction.model.dao.SongDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class SongControler {

    private static final String dbError = "An error occured while accessing the database. " +
                                            "Please try again later!";

    @Autowired
    private SongDAO songDAO;

    @RequestMapping(value = "/song/{id}", method = RequestMethod.GET)
    public String loadSongPage(Model m, @PathVariable("id") Integer songId,
                                  HttpSession session) throws Exception {
        try {
            // Grab the song from the database
            Song song = songDAO.getSongById(songId);

            //Check if the song exists
            if(song == null) {
                throw new Exception("You've attempted to view a song that does not exist!");
            }
            // Add the song to the model
            m.addAttribute("song", song);
            // Return the song view
            return "songPage";
        }
        catch (SQLException e) {
            // Error while reading the song from the database
            throw new Exception(dbError, e);
        }
    }

}
