package com.soundaddiction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage()
    {

        return "home";
    }

    @RequestMapping(value = "/petko", method = RequestMethod.GET)
    public String petko()
    {
        return "other";
    }

}
