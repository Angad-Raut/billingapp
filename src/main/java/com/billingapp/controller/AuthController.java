package com.billingapp.controller;

import com.billingapp.jwt.JwtProvider;
import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.payload.jwtDto.JwtResponseDto;
import com.billingapp.payload.loginDto.LoginDto;
import com.billingapp.service.UserDetailsService;
import com.billingapp.util.ErrorHandlerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtResponseDto>> authenticateUser(@RequestBody LoginDto login, BindingResult result) {
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            Long mobileNumber = Long.parseLong(login.getUserName());
            JwtResponseDto jwtDetails = new JwtResponseDto();
            jwtDetails.setJwtToken(jwt);
            jwtDetails.setLoginResponseDto(userDetailsService.getLoginDetails(mobileNumber));
            return new ResponseEntity<ResponseDto<JwtResponseDto>>(new ResponseDto<JwtResponseDto>(jwtDetails,null), HttpStatus.OK);
        }catch (Exception e){
            return errorHandler.handleError(e);
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto<Boolean>> logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Boolean result = false;
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            result = true;
        }
        return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(result,""), HttpStatus.OK);
    }
}
