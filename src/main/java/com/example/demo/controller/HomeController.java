/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.model.PageModel;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
    @Autowired
    PageModel pagemodel;
    @RequestMapping(value="/",method=RequestMethod.GET)
    public String home(Model model){
        pagemodel.setSIZE(1);
        pagemodel.initPageAndSize();
        Page<Post> Posts = postRepository.findAllByOrderByCreateDateDesc(PageRequest.of(pagemodel.getPAGE(), pagemodel.getSIZE()));
        model.addAttribute("posts", Posts);
        return "home";
    }
}
