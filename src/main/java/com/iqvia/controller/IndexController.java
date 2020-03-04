package com.iqvia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String welcome() {
		return "Welcome to message schedule service, for a nice UI please use: http://localhost:8080/swagger-ui.html#/";
	}

}
