package com.group41.Greenr.web;

import java.io.IOException;
import java.security.Principal;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//import com.group41.Greenr.model.EmailApplication;
import com.group41.Greenr.model.User;
import com.group41.Greenr.repository.UserRepo;
import com.group41.Greenr.service.UserService;
import com.group41.Greenr.service.UserServiceImplement;
//import com.group41.Greenr.web.dto.UserRegistrationDto;

@Controller
//@RequestMapping("/profile")
public class ProfileController {
	@Autowired
    private JavaMailSender javaMailSender;
	private UserService userService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserServiceImplement userServiceImplement;

	public ProfileController(UserService userService) {
		super();
		this.userService = userService;
	}
	

//	@ModelAttribute("user")
//	public UserRegistrationDto userRegistrationDto() {
//		return new UserRegistrationDto();
//	}
	
	// @GetMapping is used to handle GET type of request method
	@GetMapping
	public String showProfileForm() {
	return "profile";
	}
	

	@GetMapping("/profile")
    public String getUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        Long userID= userRepo.findByEmail(email).getId();

        model.addAttribute("userprofile", userRepo.findById(userID).get());

        return "profile";
    }

	@PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Principal principal) throws AddressException, MessagingException, IOException 
	{
        userServiceImplement.saveuser(user);
	    String email = principal.getName();
		sendEmail(email);
	    return "redirect:/profile";
	}
	
	public void sendEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        //this needs to be replaced with the users email that needs to be acc
        msg.setTo(email);
        msg.setSubject("Greenr Profile Changes Saved");
        msg.setText("Hello! Your profile has been changed on Greenr. To view changes please login to your account. -Greenr Team");

        javaMailSender.send(msg);
    }
//	// @PostMapping is used to handle POST type of request method
//	@PostMapping
//	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
//		userService.save(registrationDto);
//		return "redirect:/registration?success";
//	}

}
