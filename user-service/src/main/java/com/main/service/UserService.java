package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import com.main.model.UserModel;
import com.main.repository.UserDto;

@Service
public class UserService {
	
	@Autowired
	UserDto dto;

	@Autowired
	AuthenticationManager authenticationManager;


	@Autowired
	JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	
	public List<UserModel> getUsers(){
		return dto.findAll();
	}

	public UserModel register(UserModel user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return dto.save(user);
	}

	public UserModel getUserById(Long id) {
		return dto.findById(id).orElse(null);
	}

	public String deleteUser(Long id) {
		if(dto.existsById(id)) {
			dto.deleteById(id);
			return "Deleted User";
		} else {
			return "User not found";
		}
	}

	public String verifyUser(UserModel user) {
		Authentication authentication =
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getEmail());
		} else {
			return "Invalid email or password";
		}
	}

    public Integer isUserExists(Long id) {
        // TODO Auto-generated method stub
        return dto.existsById(id) ? 1 : 0;
    }
	

}
