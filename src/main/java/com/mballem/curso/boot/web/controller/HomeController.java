package com.mballem.curso.boot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	//quando o projeto é criado com spring boot, contexto é root
	//getmapping, vai acessar a raiz do projeto
	@GetMapping("/")
	public String home() {
		return "/home";
	}
	
}
