package uga.group11.cs4370.controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import uga.group11.cs4370.services.ChefsService;
import uga.group11.cs4370.models.Recipe;

@Controller
@RequestMapping
public class HomeController {


    private final ChefsService chefsService; 

    @Autowired
    public HomeController(ChefsService chefsService){
        this.chefsService = chefsService;
    }

@GetMapping
public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
 ModelAndView mv = new ModelAndView("home_page");
 
   List<Recipe> recipes = new ArrayList<>();

    try{    
       recipes = chefsService.getCreatedRecipes();
    }catch(SQLException e){
       System.out.println("Failed to retrieve recipes");
    }
    mv.addObject("recipes", recipes);     

    String errorMessage = error;
    mv.addObject("errorMessage", errorMessage);

    return mv;
    
}
}
