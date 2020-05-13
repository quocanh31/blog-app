/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

/**
 *
 * @author QuocAnh
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author QuocAnh
 */
@Entity // This tells Hibernate to make a table out of this class
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String name;
  
  private String username;
  
  private String lastname;

  private String email;
  
  private String password;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
  
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getLastName() {
    return lastname;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPassword() {
      return password;
  }
  
  public void setPassword(String password) {
      this.password = password;
  }
}