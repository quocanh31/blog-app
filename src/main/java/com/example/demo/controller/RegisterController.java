/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import java.util.HashSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
@Controller
public class RegisterController {
      
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired // This means to get the bean called userRepository
           // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public String login(){
        return "registratrion";
    }   

    @RequestMapping(value="/register",method=RequestMethod.POST)
    public String addNewUser (@RequestParam String name
        , @RequestParam String email,  @RequestParam String password, @RequestParam String lastname, @RequestParam String username,RedirectAttributes redirectAttrs) {
      // @ResponseBody means the returned String is the response, not a view name
      // @RequestParam means it is a parameter from the GET or POST request
      System.out.println("AAAAAAA");
      if (roleRepository.findByName("ROLE_ADMIN") == null) {
          roleRepository.save(new Role("ROLE_ADMIN"));
      }

      if (roleRepository.findByName("ROLE_MEMBER") == null) {
        roleRepository.save(new Role("ROLE_MEMBER"));
      }
      if(userRepository.findByUsername(username)!=null)
      {         
        User n = new User();
        n.setLastname(lastname);
        n.setUsername(username);
        n.setName(name);
        n.setEmail(email);
        n.setPassword(passwordEncoder.encode(password));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_MEMBER"));
        n.setRoles(roles);
        userRepository.save(n);
        redirectAttrs.addFlashAttribute("message", "Register success!!!");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        return "redirect:login";          
      }
        redirectAttrs.addFlashAttribute("message", "Username has been existed!!");
        redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:registratrion";
    }
}
