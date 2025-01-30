package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService{
	
	
//	private final UserRepo userRepo;
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.findByUsername(username)
			    .orElseThrow(()-> new UsernameNotFoundException("username not found"));
			
			List<SimpleGrantedAuthority> authorities =
					             List.of(new SimpleGrantedAuthority(user.getRole()));
			
			return new org.springframework.security.core.userdetails
					.User(user.getUsername(), user.getPassword(), authorities);
	}

}
