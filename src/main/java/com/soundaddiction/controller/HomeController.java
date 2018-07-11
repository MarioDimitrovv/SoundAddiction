package com.soundaddiction.controller;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.Genre;
import com.soundaddiction.model.Song;
import com.soundaddiction.model.dao.GenreDAO;
import com.soundaddiction.model.dao.SongDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private SongDAO songDAO;
    @Autowired
    private GenreDAO genreDAO;

    private static final String dbError = "An error occurred while loading the songs " +
                                            "from the database. Please try again!";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String showMainPage(HttpSession session,
                               Model model) throws Exception{
        try {
            List<Song> songs = songDAO.getAllSongs();
            List<Genre> genres = genreDAO.getAllGenres();
            model.addAttribute("songs", songs);
            session.setAttribute("genres", genres);
        } catch (SQLException | InvalidSongDataException | InvalidUserDataException e) {
            System.out.println(e.getMessage());
            throw new Exception(dbError, e);
        }
        return "mainPage";
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public String showSongsByACategory(Model model,
                                       @PathVariable("id") Integer genreId) throws Exception{
        try {
            Genre genre = genreDAO.getGenreById(genreId);
            List<Song> songs = songDAO.getSongsByGenre(genre);
            //List<Genre> genres = genreDAO.getAllGenres();
            model.addAttribute("songs", songs);
           //model.addAttribute("genres", genres);
        } catch (SQLException | InvalidSongDataException | InvalidUserDataException e) {
            System.out.println(e.getMessage());
            throw new Exception(dbError, e);
        }
        return "mainPage";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String showSongsBySearch(Model model,
                                    @RequestParam("word") String substring,
                                    @RequestParam("searchChoice") String choice) throws Exception{
        try {
            List<Song> songs;
            if(choice.equalsIgnoreCase("name")){
                songs = songDAO.getSongsByName(substring);
            }else{
                songs = songDAO.getSongsBySingerName(substring);
            }

            model.addAttribute("songs", songs);
        } catch (SQLException | InvalidSongDataException | InvalidUserDataException e) {
            System.out.println(e.getMessage());
            throw new Exception(dbError, e);
        }
        return "mainPage";
    }

}
