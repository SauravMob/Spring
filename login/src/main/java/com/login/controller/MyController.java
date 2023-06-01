package com.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.login.entities.LoginData;

import jakarta.validation.Valid;

@Controller
public class MyController {

	@GetMapping("/form")
	public String openForm(Model model) {
		System.out.println("Opening Form");
		model.addAttribute("loginData", new LoginData());
		return "form";
	}
	
	//Handler for Process form
	@PostMapping("/process")
	public String processForm(@Valid @ModelAttribute("loginData") LoginData loginData, BindingResult result) {
		if(result.hasErrors()) {
			System.out.println(result);
			return "form";
		}
		System.out.println(loginData);
		return "success";
	}
}
