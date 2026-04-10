package com.practice.pet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
public class WelcomeController {

    @GetMapping
    public String welcome(){
        return "Welcome to Personal Expense Tracker";
    }
}
