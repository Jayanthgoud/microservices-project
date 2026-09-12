package com.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.model.UserModel;
import com.main.service.UserService;

@RestController
@RequestMapping(path="/api/v1/users")
public class UserController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/")
	public String home() {
		return "Hello World!";
	}
    @PostMapping("/register")
    public UserModel register(@RequestBody UserModel user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserModel user) {
        return service.verifyUser(user);
    }

    @GetMapping
    public List<UserModel> getUsers() {
        return service.getUsers();
    }
    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/exists/{id}")
    public Integer isUserExists(@PathVariable Long id) {
        return service.isUserExists(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteById(@PathVariable Long id){
        return service.deleteUser(id);
    }

}
