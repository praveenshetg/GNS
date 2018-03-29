package com.papi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
	@RequestMapping()
	public String welcomePage() {
		return "Welcome to Duolingo.";
	}
}
