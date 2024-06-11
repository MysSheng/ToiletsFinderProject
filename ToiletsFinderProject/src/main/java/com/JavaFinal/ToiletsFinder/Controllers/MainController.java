package com.JavaFinal.ToiletsFinder.Controllers;

import com.JavaFinal.ToiletsFinder.SQLOperations;
import com.JavaFinal.ToiletsFinder.ToiletUpdateUpload;
import com.JavaFinal.ToiletsFinder.Location;
import com.JavaFinal.ToiletsFinder.locations;
import com.JavaFinal.ToiletsFinder.models.getLocationModel;
import com.JavaFinal.ToiletsFinder.models.tableCol;
import com.JavaFinal.ToiletsFinder.models.updateInput;
import com.JavaFinal.ToiletsFinder.models.userInput;
import com.JavaFinal.ToiletsFinder.LocationRepository;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaFinal.ToiletsFinder.DistanceOperation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

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

    @GetMapping("/update")
    public String showForm() {
        return "update";
    }

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/submitLocation")
    @ResponseBody
    public ModelAndView submitLocation(
            @RequestParam("longitude") String longitudeStr, 
            @RequestParam("latitude") String latitudeStr,
            @RequestParam("name") String name,
            @RequestParam("free") String freeStr,
            @RequestParam("comment") String comment) {
                ModelAndView modelAndView = new ModelAndView();
        try {
            double longitude = Double.parseDouble(longitudeStr);
            double latitude = Double.parseDouble(latitudeStr);
            boolean free = Boolean.parseBoolean(freeStr);
            locations location = new locations(longitude, latitude, name, comment, free,0,true,true,true);
            System.out.println(location.getLongitude()+" "+location.getLatitude()+" "+location.getName());
            //資料成功抓到，接下來是在資料庫建立一筆

            //locationRepository.save(location);
            String sql = "insert into locations (longitude, latitude, name,comment,isFree,floor,accessibility,isGenderFriendly,isDisabledFriendly) values(?, ?, ?, ?, ?, ?, ?, ? ,?)";
            // 加载JDBC驱动
            Properties properties = new Properties();
            try (InputStream input = ToiletUpdateUpload.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    System.out.println("Sorry, unable to find db.properties");                   
                }
                properties.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String jdbcUrl = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            try{
                Connection connection=null;
                Statement statement = null;
                connection = DriverManager.getConnection(jdbcUrl, user, password);
                statement = connection.createStatement();
                //使用數據庫
                String useDatabaseSQL = "USE toilets";
                statement.executeUpdate(useDatabaseSQL);

                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setDouble(1, location.getLongitude());
                stmt.setDouble(2, location.getLatitude());
                stmt.setString(3, location.getName());
                stmt.setString(4, location.getComment());
                stmt.setBoolean(5, location.isFree());
                stmt.setInt(6, location.getFloor());
                stmt.setBoolean(7, location.isAccessibility());
                stmt.setBoolean(8, location.isGenderFriendly());
                stmt.setBoolean(9, location.isDisabledFriendly());
                int result;
                result = stmt.executeUpdate();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            modelAndView.setViewName("redirect:/mainpage");

            return modelAndView;

        } catch (NumberFormatException e) {
            return modelAndView;
        }
    }


}
