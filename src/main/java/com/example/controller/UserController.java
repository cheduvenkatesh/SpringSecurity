package com.example.controller;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.constants.ApplicationConstants;
import com.example.dto.LoginDto;
import com.example.model.User;
import com.example.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private  UserService userService;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private  AuthenticationManager authenticationManager;

	
	@GetMapping
	public List<User> get() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userService.getUserById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto,HttpServletResponse response) {

		Authentication authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		SecretKey secretKey = Keys.hmacShaKeyFor(ApplicationConstants.JWT_SECRET
			       .getBytes(StandardCharsets.UTF_8));
	
	    String jwt = Jwts.builder()
	      .claim("username", authentication.getName())
	      .claim("authorities",authentication.getAuthorities().stream()
	    		.map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
	      .issuedAt(new Date())
	      .expiration(new Date(new Date().getTime()+10800000))// 3hrs in milliseconds
	      .signWith(secretKey)
	      .compact();
	//-------------------------
	    response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
		
		return new ResponseEntity<String>("login successfull",HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userService.saveUser(user);
		if(user!=null) {
			return new ResponseEntity<String>("User Registered", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("User not Registered", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody User user) {
		if (userService.getUserById(id).isPresent()) {
			user.setId(id);
			userService.saveUser(user);
			return new ResponseEntity<String>("User updated successfully",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>("user with this id doesn't exsist",HttpStatus.BAD_REQUEST);
		}
    }


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	    if (userService.getUserById(id).isPresent()) {
	    	userService.deleteUser(id);
	        return ResponseEntity.ok().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
