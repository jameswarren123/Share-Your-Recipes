package uga.group11.cs4370.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uga.group11.cs4370.services.UserService;
import uga.group11.cs4370.services.RecipeService;
import uga.group11.cs4370.models.ExpandedRecipe;
import uga.group11.cs4370.models.Recipe;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    
    private final UserService userService;
    private final RecipeService recipeService;

    @Autowired
    public ProfileController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ModelAndView profileOfLoggedInUser() throws SQLException {
        System.out.println("User is attempting to view profile of the logged-in user.");
        return profileOfSpecificUser(userService.getLoggedInUser().getUserId());
    }


    @GetMapping("/{userId}")
    public ModelAndView profileOfSpecificUser(@PathVariable("userId") String userId) throws SQLException {
        System.out.println("User is attempting to view profile: " + userId);

        ModelAndView mv = new ModelAndView("profile_page");

        // Fetch actual recipes from the database
        List<ExpandedRecipe> recipes = recipeService.getUserRecipes(userId);

        mv.addObject("recipes", recipes);

        if (recipes.isEmpty()) {
            mv.addObject("isNoContent", true);
        }

        return mv;
    }
}
