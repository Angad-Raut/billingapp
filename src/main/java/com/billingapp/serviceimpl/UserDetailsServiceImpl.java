package com.billingapp.serviceimpl;

import com.billingapp.entity.UserMapper;
import com.billingapp.entity.Users;
import com.billingapp.repository.UserDetailsRepository;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long mobileNo = Long.parseLong(username);
        Users user = userDetailsRepository.getUserByMobile(mobileNo);
        if(user==null){
            throw new UsernameNotFoundException(Constants.USER_NOT_FOUND_WITH_USE_NAME);
        }
        return UserMapper.userToPrincipal(user);
    }
}
