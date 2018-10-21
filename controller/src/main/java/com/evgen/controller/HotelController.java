package com.evgen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelController {

  @GetMapping("/")
  public String index(Model model) {

    model.addAttribute("message", "Hello World!");

    return "index";
  }

}
