package com.soundaddiction.controller;

import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.User;
import com.soundaddiction.model.dao.UserDAO;
import com.soundaddiction.util.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class UserController {

    private static final String dbError = "An error occured while accessing the database. " +
                                                                    "Please try again later!";

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public String redirectToHome(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(Model model,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpServletRequest request,
                            HttpSession session) throws SQLException {
        // Check if the credentials are valid
        User user = null;
        try {
            user = userDAO.getUserByEmail(email, password);

        } catch (InvalidSongDataException | InvalidUserDataException | SQLException e) {
            System.out.println(e.getMessage());
            //Show an error for invalid username or password
            String message = "Please check your entries and try again.";
            model.addAttribute("loginError",message);
            return "login";
        }


        if (user != null) {
            // Get a new session
            session = request.getSession();

            // Set the user in the session
            session.setAttribute("USER", user);

            // Set the IP of the request which called the server
            session.setAttribute("ip", request.getRemoteAddr());
            // Redirect to the main page service
            return "redirect:mainPage";
        }
        return "login";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(Model model,
                         @RequestParam("firstname") String firstName,
                         @RequestParam("lastname") String lastName,
                         @RequestParam("email") String email,
                         @RequestParam("password1") String password,
                         @RequestParam("password2") String passwordConfirm
                            ) throws SQLException {

        try {
            if(!Checker.isValidPassword(password)){
                throw new InvalidUserDataException("Wrong pass while registering user!"+password);
            }
            if(!password.equals(passwordConfirm)){
                throw new InvalidUserDataException("Passwords doesn't match while registering user!");
            }

            User user = new User(email, password, firstName, lastName);
            userDAO.registerUser(user);

            System.out.println("Successfully registered user!");


        } catch (InvalidUserDataException | SQLException e) {
            //If an error occurs while registering the user --> throw a DB exception
            System.out.println(e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String userLogout(HttpSession session){
        //Invalidate the session
        session.invalidate();

        //Return to the login page
        return "login";
    }

}
