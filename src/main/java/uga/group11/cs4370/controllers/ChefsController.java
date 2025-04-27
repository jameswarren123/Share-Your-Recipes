package uga.group11.cs4370.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/chefs")
public class ChefsController {
@GetMapping
public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
 ModelAndView mv = new ModelAndView("chefs_page");

      
    return mv;
    
}
}