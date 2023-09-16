package com.akinwalehabib.edgeservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akinwalehabib.edgeservice.config.PolarProperties;

@RestController
@RequestMapping("/")
public class HomeController {
  private PolarProperties polarProperties;

  public HomeController(PolarProperties polarProperties) {
    this.polarProperties = polarProperties;
  }
  
  @GetMapping
  String home() {
    return polarProperties.getGreeting();
  }
}
