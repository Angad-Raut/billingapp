package com.billingapp.controller;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.payload.customerDto.CustomerDataDto;
import com.billingapp.payload.customerDto.CustomerDetailsDto;
import com.billingapp.payload.customerDto.SearchCustomerDataDto;
import com.billingapp.service.CustomerService;
import com.billingapp.util.ErrorHandlerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customerDetails")
public class CustomerDetailsController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/addCustomer")
    public ResponseEntity<ResponseDto<Boolean>> addCustomer(@RequestBody CustomerDetailsDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = customerService.addCustomer(dto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.CREATED);
        }catch(AlreadyExistException | InvalidDataException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getCustomerById")
    public ResponseEntity<ResponseDto<CustomerDataDto>> getCustomerById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            CustomerDataDto data = customerService.getById(entityIdDto);
            return new ResponseEntity<ResponseDto<CustomerDataDto>>(new ResponseDto<CustomerDataDto>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/updateCustomerStatusById")
    public ResponseEntity<ResponseDto<Boolean>> updateCustomerStatusById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = customerService.updateStatus(entityIdDto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getAllCustomerDetailsByUserId")
    public ResponseEntity<ResponseDto<List<CustomerDataDto>>> getAllCustomerDetailsByUserId(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<CustomerDataDto> data = customerService.getAllCustomersByUserId(entityIdDto);
            return new ResponseEntity<ResponseDto<List<CustomerDataDto>>>(new ResponseDto<List<CustomerDataDto>>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getCustomerDetailsByMobile")
    public ResponseEntity<ResponseDto<SearchCustomerDataDto>> getCustomerDetailsByMobile(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            SearchCustomerDataDto data = customerService.getCustomerDetailsByMobile(entityIdDto);
            return new ResponseEntity<ResponseDto<SearchCustomerDataDto>>(new ResponseDto<SearchCustomerDataDto>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }
}
