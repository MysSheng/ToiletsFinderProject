package com.JavaFinal.ToiletsFinder.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JavaFinal.ToiletsFinder.models.locationModel;
import com.JavaFinal.ToiletsFinder.models.userInput;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageResponse;

@Controller
public class Controllers {
	
	@GetMapping("/lc")
	public String Location(Model model) {
		System.out.println("GET lc");
		return "index";
	}
	
	//could implement this func with a @RestController to have it's values returned. 
	@PostMapping(value = "/lc", consumes = "application/json")
	public String UserLocation(@RequestBody locationModel location) {
		System.out.println("POST lc");
		System.out.println(location.getLongitude());
		System.out.println(location.getLatitude());
		return "index";
	}
	
	@GetMapping("/keyword")
	public String Keyword(Model model) {
		System.out.println("GET keyword");
		model.addAttribute("userinput", new userInput());
		return "keyword";
	}
	@PostMapping("/keyword")
	public String SearchResult(@ModelAttribute userInput input, Model model) {
		System.out.println(input.getMessage());
		
		JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("7cdbdb52bb3243bb8a8160beec3c709a");

		JOpenCageForwardRequest request = new JOpenCageForwardRequest(input.getMessage());
		request.setMinConfidence(1);
		request.setNoAnnotations(false);
		request.setNoDedupe(true);
		JOpenCageResponse response = jOpenCageGeocoder.forward(request);
		System.out.println(response.getFirstPosition().getLat());
		System.out.println(response.getFirstPosition().getLng());
		model.addAttribute("userinput", new userInput());
		model.addAttribute("searchResult", "founded");
		return "keyword";
	}
}