package com.JavaFinal.ToiletsFinder.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.JavaFinal.ToiletsFinder.models.userInput;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageResponse;

@Controller
public class Controllers {
	
	@GetMapping("/lc")
	public String Location(Model model) {
		System.out.println("GET lc");
		return "lc";
	}
	
	//could implement this func with a @RestController to have it's values returned.
	
	@GetMapping("/keywords")
	public String Keyword(Model model) {
		System.out.println("GET keyword");
		model.addAttribute("userinput", new userInput());
		return "keyword";
	}
	@PostMapping("/keywords")
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
		return "mainpage";
	}

}