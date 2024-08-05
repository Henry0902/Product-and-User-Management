package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DangKyController {
	@GetMapping(value = "/register") 
	public String get(Model model) {
		return "register";
	}
}
