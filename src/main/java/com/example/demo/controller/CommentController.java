package com.example.demo.controller;

import java.security.Principal;
import java.util.Date;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/commentPost/{postid}")
    public String newComment(@PathVariable("postid") Integer postid, Model model){
        Post post = postRepository.getOne(postid);
        if (post == null) return "error-404";
        model.addAttribute("post", post);
        return "comment-post";
    }
    @PostMapping("/commentPost/{postid}")
    public String addNewComment(@PathVariable("postid") Integer postid, @RequestParam final String body, final RedirectAttributes redirectAttrs, Principal principal){
        Post post = postRepository.getOne(postid);
        if(body.isEmpty() || body == null){
            redirectAttrs.addFlashAttribute("message", "Comment can't be empty!");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:" + postid;
        }
        Comment comment = new Comment();
        comment.setBody(body);
        User user = userRepository.findByUsername(principal.getName());
        comment.setUser(user);
        comment.setPost(post);
        Date date = new Date();
        comment.setCreateDate(date);
        commentRepository.save(comment);
        return "redirect:/blog/" + post.getUser().getUsername()+ "/" + postid;
    }
}