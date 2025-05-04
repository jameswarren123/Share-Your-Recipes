package uga.group11.cs4370.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uga.group11.cs4370.models.ExpandedRecipe;
import uga.group11.cs4370.models.Recipe;
import uga.group11.cs4370.models.User;
import uga.group11.cs4370.services.RecipeService;
import uga.group11.cs4370.services.UserService;
import uga.group11.cs4370.services.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final UserService userService;
    private final RecipeService recipeService;
    private final SearchService searchService;

    @Autowired
    public SearchController(UserService userService, RecipeService recipeService, SearchService searchService) {
        this.searchService = searchService;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView("search_page");
        String errorMessage = error;
        mv.addObject("errorMessage", errorMessage);
        return mv;
    }

    @PostMapping("/searchresults")
    public String searchRecipes(
            @RequestParam(name = "favoritesOnly", required = false, defaultValue = "false") boolean favoritesOnly,
            @RequestParam(name = "followingOnly", required = false, defaultValue = "false") boolean followingOnly,
            @RequestParam(name = "lessThanTime", required = false) Integer lessThanTime,
            @RequestParam(name = "overRating", required = false) Float overRating,
            @RequestParam(name = "mealType", required = false) String mealType,
            @RequestParam(name = "cuisineType", required = false) String cuisineType,
            @RequestParam(name = "orderChef", required = false) String orderChef,
            @RequestParam(name = "orderRecipe", required = false) String orderRecipe) {
        
        System.out.println("User is searching");

        if(!orderChef.equals("false")) {
            List<User> users = searchService.getChefs();
        } else if (!orderRecipe.equals("false")) {
            List<Recipe> recipes = searchService.getRecipes();
        } else {
            return "redirect:/search?error=Please select a sorting option.";
        }
 


        return "redirect:/search?favoritesOnly=" + favoritesOnly + "&followingOnly=" + followingOnly +
                "&lessThanTime=" + lessThanTime + "&overRating=" + overRating +
                "&mealType=" + mealType + "&cuisineType=" + cuisineType +
                "&orderChef=" + orderChef + "&orderRecipe=" + orderRecipe;
    }

}