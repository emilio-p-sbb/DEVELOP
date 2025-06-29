package com.portofolio.auth.jwt;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("username = "+username);
        return userRepository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
//        return userRepository.findByUsername(username).orElseThrow(
//        		() -> new ResourceNotFoundException("User not found")
//        		);
    }
}
