package com.example.demo.repository;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findAllByOrderByCreateDateDesc();
    List<Post> findByUserOrderByCreateDateDesc(User user);
}