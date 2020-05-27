package com.example.demo.repository;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer>{
    Page<Post> findAllByOrderByCreateDateDesc(Pageable pageable);
    Page<Post> findByUserOrderByCreateDateDesc(User user, Pageable pageable);
}