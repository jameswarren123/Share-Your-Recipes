package uga.group11.cs4370.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import uga.group11.cs4370.models.Chef;
import uga.group11.cs4370.models.User;
import uga.group11.cs4370.services.ChefsService;
import uga.group11.cs4370.services.ImageService;
import uga.group11.cs4370.services.UserService;

@Controller
@RequestMapping("/chefs")
public class ChefsController {
    private final ChefsService chefsService;
    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public ChefsController(ChefsService chefsService, ImageService imageService, UserService userService) {
        this.chefsService = chefsService;
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mv = new ModelAndView("chefs_page");

        // List<User> users = new ArrayList<>();

        // try {
        // users = chefsService.getUsers();
        // } catch (SQLException e) {
        // System.out.println("Failed to retrieve users");
        // }

        // String image = "";

        // try {
        // image = imageService.getUserImage();
        // System.out.println("Path:" + image);
        // } catch (SQLException e) {
        // System.out.println("Failed to retrieve users images");
        // }

        try {
            List<Chef> subscribableUsers = chefsService.getChefsWithSubStatus();
            mv.addObject("chefs", subscribableUsers);
        } catch (SQLException e) {
            System.out.println("Failed followable chefs");
        }

        // try {
        //     List<Chef> chefs = chefsService.getChefs();
        //     mv.addObject("chefs", chefs);
        // } catch (SQLException e) {
        //     System.out.println("Failed to retrieve chefs");
        // }

        // mv.addObject("users", users);
        // mv.addObject("image", image);

        String errorMessage = error;
        mv.addObject("errorMessage", errorMessage);

        return mv;

    }

    @GetMapping("{user_id}/sub/{isSubbed}")
    public String followUnfollowUser(@PathVariable("user_id") String user_id,
            @PathVariable("isSubbed") Boolean isSubbed) {
        System.out.println("User is attempting to sub/unsub a chef:");
        System.out.println("\tuser_id: " + user_id);
        System.out.println("\tisSubbed: " + isSubbed);

        try {
            String loggedInUserId = userService.getLoggedInUser().getUserId();
            chefsService.updateFollowStatus(loggedInUserId, user_id, isSubbed);
        } catch (SQLException e) {
            return "redirect:/chefs?error=Could not update sub status.";
        }

        return "redirect:/chefs";
    }
}