package com.billingapp.controller;

import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.payload.userDto.UserDetailsDto;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.service.UserDetailsService;
import com.billingapp.util.ErrorHandlerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PostMapping(value = "/addUser")
    public ResponseEntity<ResponseDto<Boolean>> addUser(@RequestBody UserDetailsDto userDetailsDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
             Boolean data = userDetailsService.addUser(userDetailsDto);
             return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null),HttpStatus.CREATED);
         }catch(AlreadyExistException | InvalidDataException e){
             return errorHandler.handleError(e);
         }
    }
}
