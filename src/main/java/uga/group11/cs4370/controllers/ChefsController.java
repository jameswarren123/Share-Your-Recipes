package uga.group11.cs4370.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import uga.group11.cs4370.models.Recipe;
import uga.group11.cs4370.models.User;
import uga.group11.cs4370.services.ChefsService;

@Controller
@RequestMapping("/chefs")
public class ChefsController {
    private final ChefsService chefsService;

    @Autowired
    public ChefsController(ChefsService chefsService) {
        this.chefsService = chefsService;
    }

    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView("chefs_page");

        List<User> users = new ArrayList<>();

        try {
            users = chefsService.getUsers();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve users");
        }
        mv.addObject("users", users);

        String errorMessage = error;
        mv.addObject("errorMessage", errorMessage);

        return mv;

    }
}