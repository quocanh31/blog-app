package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/new-post")
    public String newPost(){
        return "new-post";
    }
    @PostMapping("/new-post")
    public String addNewPost(@RequestParam final String title, @RequestParam final String body, final RedirectAttributes redirectAttrs, Principal principal){
        if(title.isEmpty() || title == null || body.length() <= 5){
            final ArrayList<String> errors = new ArrayList<String>();
            errors.add("*Please provide title.");
            errors.add("*Title must be at least 5 characters.");
            redirectAttrs.addFlashAttribute("messages", errors);
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:new-post";
        }
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        User user = userRepository.findByUsername(principal.getName());
        post.setUser(user);
        Date date = new Date();
        post.setCreateDate(date);
        System.out.println(post.getTitle() + post.getBody() + post.getUser() + post.getCreateDate());
        postRepository.save(post);

        return "redirect:";
    }
}