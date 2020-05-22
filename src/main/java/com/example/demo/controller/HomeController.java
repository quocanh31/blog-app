/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author QuocAnh
 */
@Controller
public class HomeController {
    @Autowired
    PostRepository postRepository;
    @Autowired 
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @RequestMapping(value="/",method=RequestMethod.GET)
    public String home(Model model){
        List<Post> Posts = postRepository.findAllByOrderByCreateDateDesc();
        model.addAttribute("posts", Posts);
        return "home";
    }
    
    @RequestMapping(value = "/blog/{username}", method=RequestMethod.GET)
    public String userBlog(Model model, @PathVariable("username") String username){
        User user = userRepository.findByUsername(username);
        if (user == null) return "error-404";
        else{
            List<Post> Posts = postRepository.findByUserOrderByCreateDateDesc(user);
            model.addAttribute("posts", Posts);
            model.addAttribute("user", user);
            return "user-blog";
        }

    }
    
    @RequestMapping(value = "blog/{username}/{postid}", method=RequestMethod.GET)
    public String blogDetail(Model model , @PathVariable("username") String username, @PathVariable("postid") Integer postid){
        Post post = postRepository.getOne(postid);
        if (post == null) return "error-404";
//        List<Comment> comments = new ArrayList<>(post.getComment());
        Set<Comment> comments = post.getComment();
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "blog-detail";
    }
    
}
