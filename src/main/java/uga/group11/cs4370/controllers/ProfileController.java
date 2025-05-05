package uga.group11.cs4370.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uga.group11.cs4370.services.UserService;
import uga.group11.cs4370.services.ChefsService;
import uga.group11.cs4370.services.RecipeService;
import uga.group11.cs4370.models.ExpandedRecipe;
import uga.group11.cs4370.models.User;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final RecipeService recipeService;
    private final ChefsService chefsService;

    @Autowired
    public ProfileController(UserService userService, RecipeService recipeService, ChefsService chefsService) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.chefsService = chefsService;
    }

    @GetMapping /*("/{isCurrentUser}")*/
    public ModelAndView profileOfLoggedInUser(/*@PathVariable("isCurrentUser") boolean isCurrentUser,*/
            @RequestParam(name = "error", required = false) String error) throws SQLException {
        ModelAndView mv = new ModelAndView("profile_page");
        String user_id = userService.getLoggedInUser().getUserId();

        //mv.addObject("isCurrentUser", isCurrentUser);

        User user = userService.getUser(user_id);
        mv.addObject("user", user);

        List<ExpandedRecipe> recipes = recipeService.getUserExpRecipes(user_id);
        mv.addObject("recipes", recipes);

        if (recipes.isEmpty()) {
            mv.addObject("isNoContent", true);
        }

        String errorMessage = error;
        mv.addObject("errorMessage", errorMessage);
        return mv;
    }

    @GetMapping("/{user_id}/{isCurrentUser}")
    public ModelAndView profileOfSpecificUser(@PathVariable("user_id") String user_id,
            @PathVariable("isCurrentUser") boolean isCurrentUser) throws SQLException {
        ModelAndView mv = new ModelAndView("profile_page");
        System.out.println("User is attempting to view profile: " + user_id);

        mv.addObject("isCurrentUser", isCurrentUser);

        User user = userService.getUser(user_id);
        mv.addObject("user", user);

        List<ExpandedRecipe> recipes = recipeService.getUserExpRecipes(user_id);
        mv.addObject("recipes", recipes);

        if (recipes.isEmpty()) {
            mv.addObject("isNoContent", true);
        }

        return mv;
    }

    // @GetMapping("{user_id}/sub/{isSubbed}")
    // public String followUnfollowUser(@PathVariable("user_id") String user_id,
    // @PathVariable("isSubbed") Boolean isSubbed) {
    // // System.out.println("User is attempting to sub/unsub a chef:");
    // // System.out.println("\tuser_id: " + user_id);
    // // System.out.println("\tisSubbed: " + isSubbed);

    // try {
    // String loggedInUserId = userService.getLoggedInUser().getUserId();
    // chefsService.updateFollowStatus(loggedInUserId, user_id, isSubbed);
    // } catch (SQLException e) {
    // return "redirect:/chefs?error=Could not update sub status.";
    // }

    // return "redirect:/chefs";
    // }
}
