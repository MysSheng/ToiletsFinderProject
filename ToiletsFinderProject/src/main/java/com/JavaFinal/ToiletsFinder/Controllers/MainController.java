package com.JavaFinal.ToiletsFinder.Controllers;

import com.JavaFinal.ToiletsFinder.Location;
import com.JavaFinal.ToiletsFinder.models.getLocationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.JavaFinal.ToiletsFinder.DistanceOperation;
import com.JavaFinal.ToiletsFinder.Location;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private getLocationModel userLocation = new getLocationModel();

    @GetMapping("/mainpage")
    public String Welcome() {
        System.out.println("GET welcome");
        return "mainpage";
    }

    @PostMapping(value = "/lc", consumes = "application/json")
    public String UserLocation(@RequestBody getLocationModel location, Model model) {
        System.out.println("POST lc");
        userLocation.setLongitude(location.getLongitude());
        userLocation.setLatitude(location.getLatitude());
        userLocation.setCountryCode(location.getCountryCode());
        userLocation.setPrincipalSubdivision(location.getPrincipalSubdivision());
        userLocation.setLocality(location.getLocality());
        return "gotlocation";
    }

    @GetMapping("/gotlocation")
    public String gotlocation(Model model) {
        System.out.println("GET keyword");
        model.addAttribute("country", userLocation.getCountryCode());
        model.addAttribute("subdiv", userLocation.getPrincipalSubdivision());
        model.addAttribute("locality", userLocation.getLocality());

        Location user = new Location(userLocation.getLongitude(), userLocation.getLatitude());
        List<Location> toilets = new ArrayList<>();
        Location l1 = new Location(122,50);
        toilets.add(l1);
        Location l2 = new Location(122,60);
        toilets.add(l2);

        DistanceOperation d = new DistanceOperation();
        d.sortByDistance(user,toilets);
        model.addAttribute("toilets", toilets);
        return "gotlocation";
    }
}
