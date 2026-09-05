package com.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {
	
	@RequestMapping("/")
	public String home() {
		return "Hello from API-Gateway";
	}

}
