package com.JavaFinal.ToiletsFinder.Controllers;

import com.JavaFinal.ToiletsFinder.SQLOperations;
import com.JavaFinal.ToiletsFinder.Location;
import com.JavaFinal.ToiletsFinder.models.getLocationModel;
import com.JavaFinal.ToiletsFinder.models.tableCol;
import com.JavaFinal.ToiletsFinder.models.userInput;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.JavaFinal.ToiletsFinder.DistanceOperation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        userLocation.setCountryName(location.getcountryName());
        userLocation.setPrincipalSubdivision(location.getPrincipalSubdivision());
        userLocation.setLocality(location.getLocality());
        return "gotlocation";
    }

    @GetMapping("/gotlocation")
    public String gotlocation(Model model) {
        System.out.println("GET location");
        if (Objects.equals(userLocation.getcountryName(), "Taiwan (Province of China)")) {
            model.addAttribute("country", "Taiwan");
        } else {
            model.addAttribute("country", userLocation.getcountryName());
        }
        model.addAttribute("subdiv", userLocation.getPrincipalSubdivision());
        model.addAttribute("locality", userLocation.getLocality());

        Location user = new Location(userLocation.getLongitude(), userLocation.getLatitude());
        //System.out.println(userLocation.getLongitude()+","+userLocation.getLatitude());
        //System.out.println(user.getLongitude()+","+user.getLatitude());
        List<Location> toilets = SQLOperations.getAlllocations();
        DistanceOperation d = new DistanceOperation();
        d.sortByDistance(user,toilets);

        List<tableCol> tableDatas = new ArrayList<>();
        for (Location l : toilets) {
            tableCol td = new tableCol();
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            td.setDistance(String.format("%.2f",d.getDistance(user,l)));
            td.setComment(l.getComment());
            if (l.isFree()){
                td.setIsFree("yes");
            } else td.setIsFree("no");
            td.setName(l.getName());
            tableDatas.add(td);
            td.setLink("http://maps.google.com/maps?q="+l.getLatitude()+","+l.getLongitude());
        }
        model.addAttribute("toilets", tableDatas);
        return "gotlocation";
    }

    @GetMapping("/keyword")
    public String KeywordSearch(Model model) {
        System.out.println("GET keyword");
        model.addAttribute("country", "");
        model.addAttribute("subdiv", "");
        model.addAttribute("locality", "");
        model.addAttribute("userinput", new userInput());
        return "keywordLocation";
    }

    @PostMapping("/keyword")
    public String SearchComplete(@ModelAttribute userInput input, Model model) {
        System.out.println(input.getMessage());

        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("7cdbdb52bb3243bb8a8160beec3c709a");

        JOpenCageForwardRequest request = new JOpenCageForwardRequest(input.getMessage());
        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        if (response.getFirstPosition() == null){
            model.addAttribute("country", "");
            model.addAttribute("subdiv", "");
            model.addAttribute("locality", "");
            model.addAttribute("userinput", new userInput());
            model.addAttribute("searchResult", "Not found...!");
            return "/keywordLocation";
        }
        System.out.println(response.getFirstPosition().getLat());
        System.out.println(response.getFirstPosition().getLng());
        userLocation.setLatitude(response.getFirstPosition().getLat());
        userLocation.setLongitude(response.getFirstPosition().getLng());
        userLocation.setPrincipalSubdivision(response.getFirstComponents().getCity());
        userLocation.setLocality(response.getFirstComponents().getNeighbourhood());
        userLocation.setCountryName(response.getFirstComponents().getCountry());

        Location user = new Location(userLocation.getLongitude(), userLocation.getLatitude());
        List<Location> toilets = SQLOperations.getAlllocations();
        DistanceOperation d = new DistanceOperation();
        d.sortByDistance(user,toilets);

        List<tableCol> tableDatas = new ArrayList<>();
        for (Location l : toilets) {
            tableCol td = new tableCol();
            td.setDistance(String.format("%.2f",d.getDistance(user,l)));
            td.setComment(l.getComment());
            if (l.isFree()){
                td.setIsFree("yes");
            } else td.setIsFree("no");
            td.setName(l.getName());
            tableDatas.add(td);
            td.setLink("http://maps.google.com/maps?q="+l.getLatitude()+","+l.getLongitude());
        }
        model.addAttribute("country", userLocation.getcountryName());
        model.addAttribute("subdiv", userLocation.getPrincipalSubdivision());
        model.addAttribute("locality", userLocation.getLocality());
        model.addAttribute("toilets", tableDatas);
        model.addAttribute("userinput", new userInput());
        model.addAttribute("searchResult", "Got it!");
        System.out.println("here");
        return "/keywordLocation";
    }

    @GetMapping("/upload")
    public String uploadPage(){
        return "upload";
    }
}
