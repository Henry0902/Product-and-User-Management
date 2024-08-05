package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrangChuController extends BaseController{
	@GetMapping(value = "/") 
	public String get(Model model) {
		return "index";
	}
}
