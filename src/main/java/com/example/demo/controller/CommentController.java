package com.example.demo.controller;

import java.security.Principal;
import java.util.Date;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @GetMapping("/new-comment")
    public String newComment(){
        return "new-comment";
    }
    @PostMapping("/new-comment")
    public String addNewComment(@RequestParam final String body, final RedirectAttributes redirectAttrs, Principal principal){
        if(body.isEmpty() || body == null){
            redirectAttrs.addFlashAttribute("message", "Comment can't be empty!");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:new-comment";
        }
        Comment comment = new Comment();
        comment.setBody(body);
        User user = userRepository.findByUsername(principal.getName());
        comment.setUser(user);
        Date date = new Date();
        comment.setCreateDate(date);
        commentRepository.save(comment);
        return "post";
    }
}