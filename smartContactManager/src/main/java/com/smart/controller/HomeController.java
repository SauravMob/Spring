package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	//Handler for registering user
	@PostMapping("/do_register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
		
		try {
			if (!agreement) {
				System.out.println("You have not agreed to the terms and conditions");
				throw new Exception("You have not agreed to the terms and conditions");
			}
			if (result.hasErrors()) {
				System.out.println(result);
				model.addAttribute("user", user);
				return "signup";
			}
			user.setRole("Role_User");
			user.setEnabled(true);
			user.setImgUrl("default.png");
			System.out.println("Agreement" + agreement);
			System.out.println("User" + user);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			
			User u1 = this.userRepository.save(user);
			
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Resgistered!", "alert-success"));
			return "signup";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong" + e.getMessage(), "alert-danger"));	
			return "signup";			
		}
	}

}
