package com.billingapp.service;

import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.userDto.UserDetailsDto;
import com.billingapp.payload.userDto.UserDataDto;
import com.billingapp.payload.userDto.ChangePasswordDto;
import com.billingapp.payload.loginDto.LoginResponseDto;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;

public interface UserDetailsService {
    Boolean addUser(UserDetailsDto userDetailsDto)throws AlreadyExistException, InvalidDataException;
    UserDataDto getByUserId(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    Boolean changePassword(ChangePasswordDto changePasswordDto)throws ResourceNotFoundException;
    LoginResponseDto getLoginDetails(Long mobile)throws ResourceNotFoundException;
}
