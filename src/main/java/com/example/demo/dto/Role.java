package com.example.demo.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Authority.READ),
    ADMIN(Authority.READ, Authority.WRITE),
    BLOCKED();

    private final Set<Authority> authorities;

    Role(Authority... authorities){
        this.authorities = new HashSet<>();
        this.authorities.addAll(Arrays.asList(authorities));
    }

    public Set<Authority> getAuthorities(){
        return authorities;
    }
}
