package com.billingapp.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserPrincipal userToPrincipal(Users user) {
        UserPrincipal userp = new UserPrincipal();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).collect(Collectors.toList());
        String username = String.valueOf(user.getMobileNumber());
        userp.setUsername(username);
        userp.setPassword(user.getPassword());
        userp.setEnabled(user.getStatus());
        userp.setAuthorities(authorities);
        return userp;
    }
}
