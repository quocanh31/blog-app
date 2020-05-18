/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



/**
 *
 * @author QuocAnh
 */
@Entity
@Table(name = "user")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", nullable = false)
   private int id;
   
   @Column(name = "name", nullable = false)
   private String name;
   
   @Column(name = "lastname", nullable = false)
   private String lastname;
   
   @Column(name = "username", nullable = false, unique = true)
   private String username;
   
   @Column(name ="email", nullable = false, unique = true)
   private String email;
   
   @Column(name ="password", nullable = false)
   private String password;
   
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(
           name = "user_role",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id")
           
   )
   private Set<Role> roles;

   @OneToMany(mappedBy = "user")
    private Set<Post> posts;

   public int getId(){
       return id;
   }
   
   public void setId(int id){
       this.id = id;
   }
   
   public String getName(){
       return name;
   }
   
   public void setName(String name){
       this.name = name;
   }
   public String getEmail(){
       return email;
   }
   
   public void setEmail(String email){
       this.email = email;
   }
   
   public String getUsername(){
       return username;
   }
   
   public void setUsername(String username){
       this.username = username;
   }
   
   public String getLastname(){
       return lastname;
   }
   
   public void setLastname(String lastname){
       this.lastname = lastname;
   }
   
   public String getPassword(){
       return password;
   }
   
   public void setPassword(String password) {
       this.password = password;
   }
   
   public Set<Role> getRoles() {
       return roles;
   }
   
   public void setRoles(Set<Role> roles){
       this.roles = roles;
   }
   
   public Set<Post> getPost() {
       return posts;
   }
   
   public void setPost(Set<Post> posts){
       this.posts = posts;
   }
   
}
