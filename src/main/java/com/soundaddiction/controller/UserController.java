package com.soundaddiction.controller;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.User;
import com.soundaddiction.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class UserController {

    private static final String dbError = "An error occured while accessing the database. " +
                                                                    "Please try again later!";

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(Model model,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpServletRequest request,
                            HttpSession session) throws SQLException {
        // Check if the credentials are valid
        User user = null;
        try {
            user = userDAO.getUserByEmailAndPass(email, password);

        } catch (InvalidSongDataException | InvalidUserDataException e) {
            //If an error occurs while loading the user --> throw a DB exception
            throw new SQLException(dbError, e);
        }


        if (user != null) {
            // Get a new session
            session = request.getSession();

            // Set the user in the session
            session.setAttribute("USER", user);

            // Set the IP of the request which called the server
            session.setAttribute("ip", request.getRemoteAddr());
            // Redirect to the main page service
            return "mainPage";
        }
        //Show an error for invalid username or password
        String message = "Please check your entries and try again.";

        model.addAttribute("loginError",message);
        return "login";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(){
        return "signUp";
    }

}
