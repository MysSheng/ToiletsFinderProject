package com.JavaFinal.ToiletsFinder.Controllers;

import com.JavaFinal.ToiletsFinder.Location;
import com.JavaFinal.ToiletsFinder.models.getLocationModel;
import com.JavaFinal.ToiletsFinder.models.userInput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    private Location user;

    @GetMapping("/mainpage")
    public String Welcome() {
        System.out.println("GET welcome");
        return "mainpage";
    }

    @PostMapping(value = "/lc", consumes = "application/json")
    public String UserLocation(@RequestBody getLocationModel location, Model model) {
        System.out.println("POST lc");
        user = new Location(location.getLongitude(), location.getLatitude());
        model.addAttribute("", new userInput());
        model.addAttribute("searchResult", "founded");
        return "gotlocation";
    }
}
