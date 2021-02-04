package com.entrecodigos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class vistaMobilController {


  @GetMapping({"","/","/index"})
  public String index() {
	  return "index";
  }
	
}
