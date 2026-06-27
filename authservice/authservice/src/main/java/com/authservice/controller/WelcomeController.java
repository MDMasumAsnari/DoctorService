package com.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class WelcomeController {

    @GetMapping("welcome")
    public String Welcome(){
        return "Welcome";
    }

    @GetMapping("msg1")
    public String Message(){
        return "Message !";
    }

}
