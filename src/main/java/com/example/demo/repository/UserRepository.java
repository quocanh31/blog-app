/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author QuocAnh
 */
@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u from User u where u.username = ?1")
    public User findByUsername(String username);
}

