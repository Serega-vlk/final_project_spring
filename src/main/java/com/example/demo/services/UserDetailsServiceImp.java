package com.example.demo.services;

import com.example.demo.dto.SecurityUser;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImp")
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailsServiceImp(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.findByLogin(s).orElseThrow(()
                -> new UsernameNotFoundException("user with username is no found"));
        return SecurityUser.fromUser(user);
    }
}
