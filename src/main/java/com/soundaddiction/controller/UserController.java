package com.soundaddiction.controller;

import com.soundaddiction.model.User;
import com.soundaddiction.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String logIn(Model model) {
        try {
            User user = userDAO.getUserById(2);
            model.addAttribute("user", user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "main";
    }

}
