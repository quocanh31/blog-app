package com.example.demo.controller;

import com.example.demo.model.Comment;
import java.util.ArrayList;
import java.util.Date;

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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
            errors.add("*Body must be at least 5 characters.");
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
    public String blogDetail(Principal principal, Model model , @PathVariable("username") String username, @PathVariable("postid") Integer postid){
        Post post = postRepository.getOne(postid);
        if (post == null) return "error-404";
//        List<Comment> comments = new ArrayList<>(post.getComment());
        Set<Comment> comments = post.getComment();
        if (isPostOwner(principal, post)){
            model.addAttribute("isowner", post.getUser().getUsername());
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "blog-detail";
    }
    
    @RequestMapping(value ="editPost/{postid}", method=RequestMethod.GET)
    public String editPostGet(Principal principal, Model model, @PathVariable("postid") Integer postid){
        Optional<Post> post = postRepository.findById(postid);
        if (post.isPresent()){
            if (!isPostOwner(principal, post.get())) return "error";
            model.addAttribute("post", post.get());
            return "edit-post";
        }
        return "error-404";
    }
    
    @RequestMapping(value ="editPost/{postid}", method=RequestMethod.POST)
    public String editPostPost(@PathVariable("postid") Integer postid, @RequestParam final String title, @RequestParam final String body,final RedirectAttributes redirectAttrs, Principal principal){
            if(title.isEmpty() || title == null || body.length() <= 5){
            final ArrayList<String> errors = new ArrayList<String>();
            errors.add("*Please provide title.");
            errors.add("*Body must be at least 5 characters.");
            redirectAttrs.addFlashAttribute("messages", errors);
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/editPost/" + postid;
        }
        Post post = postRepository.getOne(postid);
        post.setTitle(title);
        post.setBody(body);
        postRepository.save(post);
        return "redirect:/blog/" + principal.getName();
    }
    
    @RequestMapping(value="deletePost/{postid}", method=RequestMethod.GET)
    public String deletePost(@PathVariable("postid") Integer postid , Principal principal){
        Post post = postRepository.getOne(postid);
        if (!isPostOwner(principal, post)) return "error";
        postRepository.deleteById(postid);
        return "redirect:/blog/" + principal.getName();
    }
    
    public boolean isPostOwner(Principal principal, Post post){
        if(principal != null && principal.getName().equals(post.getUser().getUsername()))
            return true;
        return false;
    }
}