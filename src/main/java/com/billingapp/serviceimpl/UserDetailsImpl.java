package com.billingapp.serviceimpl;

import com.billingapp.entity.Role;
import com.billingapp.entity.RoleName;
import com.billingapp.entity.Users;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.loginDto.LoginResponseDto;
import com.billingapp.payload.userDto.ChangePasswordDto;
import com.billingapp.payload.userDto.UserDataDto;
import com.billingapp.payload.userDto.UserDetailsDto;
import com.billingapp.repository.RoleRepository;
import com.billingapp.repository.UserDetailsRepository;
import com.billingapp.service.UserDetailsService;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bcrPasswordEncoder;
    @Override
    public Boolean addUser(UserDetailsDto dto) throws AlreadyExistException, InvalidDataException {
        Users userDetails=null;
        try {
            if(dto.getUserId()==null){//insert operation
                userDetails= Users.builder()
                        .userId(dto.getUserId())
                        .userType(Constants.ADMIN_TYPE)
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .mobileNumber(dto.getMobileNumber())
                        .email(dto.getEmail())
                        .shopName(dto.getShopName())
                        .status(Constants.ACTIVE_STATUS)
                        .roles(setRole())
                        .insertedTime(new Date())
                        .updatedTime(new Date())
                        .password(bcrPasswordEncoder.encode(dto.getPassword()))
                        .build();
            }else{//upadte operation
                 Users userDetails1 = userDetailsRepository.getByUserId(dto.getUserId());
                 if(!dto.getFirstName().equals(userDetails1.getFirstName())){
                     userDetails.setFirstName(dto.getFirstName());
                 }
                 if(!dto.getLastName().equals(userDetails1.getLastName())){
                    userDetails.setLastName(dto.getLastName());
                 }
                 if(!dto.getMobileNumber().equals(userDetails1.getMobileNumber())){
                    userDetails.setMobileNumber(dto.getMobileNumber());
                 }
                 if(!dto.getEmail().equals(userDetails1.getEmail())){
                    userDetails.setEmail(dto.getEmail());
                 }
                 if(!dto.getShopName().equals(userDetails1.getShopName())){
                    userDetails.setShopName(dto.getShopName());
                 }
                 userDetails.setUpdatedTime(new Date());
            }
            Users details=userDetailsRepository.save(userDetails);
            if(details!=null){
                return true;
            }else{
                return false;
            }
        } catch(Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public UserDataDto getByUserId(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        Users userDetails = userDetailsRepository.getByUserId(entityIdDto.getEntityId());
        if(userDetails!=null){
             return UserDataDto.builder()
                     .userId(userDetails.getUserId())
                     .firstName(userDetails.getFirstName())
                     .lastName(userDetails.getLastName())
                     .shopName(userDetails.getShopName())
                     .mobileNumber(userDetails.getMobileNumber())
                     .email(userDetails.getEmail()!=null?userDetails.getEmail():null)
                     .build();
        }else{
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        }
    }

    @Override
    public Boolean changePassword(ChangePasswordDto changePasswordDto) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public LoginResponseDto getLoginDetails(Long mobile) throws ResourceNotFoundException {
        Users userDetails = userDetailsRepository.getUserByMobile(mobile);
        if(userDetails!=null){
            return LoginResponseDto.builder()
                    .userId(userDetails.getUserId())
                    .firstName(userDetails.getFirstName())
                    .lastName(userDetails.getLastName())
                    .shopName(userDetails.getShopName())
                    .mobileNumber(userDetails.getMobileNumber())
                    .email(userDetails.getEmail()!=null?userDetails.getEmail():null)
                    .userType(userDetails.getUserType()!=null?userDetails.getUserType():null)
                    .build();
        }else{
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        }
    }
    private Set<Role> setRole(){
        Set<Role> roles = new HashSet<Role>();
        String adminRole = RoleName.ADMIN.toString();
        Role role = roleRepository.getByRoleName(adminRole);
        if(role==null){
            role = roleRepository.save(Role.builder().roleName(adminRole).build());
        }
        roles.add(role);
        return roles;
    }
}
