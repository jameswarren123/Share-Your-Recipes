package uga.group11.cs4370.controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;

import uga.group11.cs4370.services.ChefsService;
import uga.group11.cs4370.services.UserService;


@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final ChefsService chefsService;
    private final UserService userService;
     
    @Autowired
    public RecipeController(ChefsService chefsService, UserService userService){
        this.chefsService = chefsService;
        this.userService = userService;
    }

   @GetMapping
   public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
       ModelAndView mv = new ModelAndView("recipe_page");
    
       String errorMessage = error;
       mv.addObject("errorMessage", errorMessage);
      
    return mv;
    
}

    @PostMapping("/createrecipe")
    public String createRecipe(@RequestParam(name = "titleText") String titleText,@RequestParam(name = "directionsText") String directionsText,@RequestParam(name = "timeText") int timeText, @RequestParam(name = "rec_img") File rec_img) {
        System.out.println("User is creating recipe: " + titleText);
        
         
        // Redirect the user if the post creation is a success.
        // return "redirect:/";
        try{
            boolean postSuccess = chefsService.createRecipe(userService.getLoggedInUser().getUserId(),titleText,directionsText,timeText);
            if(postSuccess){
                return "redirect:/";
            }else{
                String message = URLEncoder.encode("Failed to create the Recipe. Please try again.",
                StandardCharsets.UTF_8);
                return "redirect:/recipe?error=" + message;
            }
        }catch(Exception e){
        // Redirect the user with an error message if there was an error.
        String message = URLEncoder.encode("Failed to create the Recipe. Please try again.",
                StandardCharsets.UTF_8);
        return "redirect:/recipe?error=" + message;
        }
    }



}

