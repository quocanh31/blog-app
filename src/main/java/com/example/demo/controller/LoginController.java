/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author QuocAnh
 */
@Controller
public class LoginController {
    @GetMapping("/login") 
    public String getLogin() {
        return "login";
    }   
}
