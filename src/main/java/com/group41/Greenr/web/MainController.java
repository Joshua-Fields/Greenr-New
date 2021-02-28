package com.group41.Greenr.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.group41.Greenr.model.Post;
import com.group41.Greenr.repository.UserRepo;
import com.group41.Greenr.service.UserService;
import com.group41.Greenr.web.dto.UserRegistrationDto;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepo userRepo;
	
	// @GetMapping is used to handle GET type of request method
	@GetMapping("/login") 
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/registration";
	}
	
	@GetMapping("/post")
	public String post(Model model) {
		Post post = new Post();
		model.addAttribute("post", post);
		return "post";
	}
	
	@GetMapping("/who-we-are") 
	public String whoweare() {
		return "who-we-are";
	}
	
	@GetMapping("/newPost") 
	public String newPost() {
		return "newPost";
	}
	
	@GetMapping("/contact-us") 
	public String contactUs() {
		return "contact-us";
	}
	
	@GetMapping("/addPost") 
	public String addPost() {
		return "addPost";
	}
	
	@GetMapping("/editDescription") 
	public String editDescription() {
		return "editDescription";
	}
	
	@GetMapping("/listPost") 
	public String listPost() {
		return "listPost";
	}
	
	@GetMapping("/editTitle") 
	public String editTitle() {
		return "editTitle";
	}
	
	@GetMapping("/community") 
	public String community() {
		return "community";
	}
	
	@GetMapping("/faq") 
	public String faq() {
		return "faq";
	}
	
	@GetMapping("/aqi")
	public String aqi() {
		return "aqi_location_rating";
	}
	

	
	@GetMapping("/welcome-screen") 
	public String welcomepage() {
		return "welcome-screen";
	}
	
	@RequestMapping("/deletebyuser")
    public String currentLoggedInUsername(Principal principal) {
        String email = principal.getName();
        Long userID= userRepo.findByEmail(email).getId();
        userRepo.deleteById(userID);
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
	
	@PostMapping("/post")
	public String submitForm(@ModelAttribute("post") Post post) {
		System.out.println(post);
		return "community";
	}
		
}
