package com.billingapp.controller;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.EntityIdWithTypeDto;
import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.payload.discountDto.DiscountDataDto;
import com.billingapp.payload.discountDto.DiscountDropDownDto;
import com.billingapp.payload.discountDto.DiscountDto;
import com.billingapp.payload.discountDto.DiscountTypeDto;
import com.billingapp.service.DiscountService;
import com.billingapp.util.ErrorHandlerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/addDiscount")
    public ResponseEntity<ResponseDto<Boolean>> addDiscount(@RequestBody DiscountDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = discountService.addDiscount(dto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.CREATED);
        }catch(AlreadyExistException | InvalidDataException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getDiscountById")
    public ResponseEntity<ResponseDto<DiscountDataDto>> getDiscountById(@RequestBody EntityIdDto entityIdDto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            DiscountDataDto data = discountService.getById(entityIdDto);
            return new ResponseEntity<ResponseDto<DiscountDataDto>>(new ResponseDto<DiscountDataDto>(data,null), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/getDiscountTypes")
    public ResponseEntity<ResponseDto<List<DiscountTypeDto>>> getDiscountTypes(){
        try{
            List<DiscountTypeDto> data = discountService.getDiscountDropDown();
            return new ResponseEntity<ResponseDto<List<DiscountTypeDto>>>(new ResponseDto<List<DiscountTypeDto>>(data,null), HttpStatus.OK);
        }catch(Exception e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getAllDiscountsByUserId")
    public ResponseEntity<ResponseDto<List<DiscountDataDto>>> getAllDiscountsByUserId(@RequestBody EntityIdWithTypeDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<DiscountDataDto> data = discountService.getAllDiscountsByUserId(dto);
            return new ResponseEntity<ResponseDto<List<DiscountDataDto>>>(new ResponseDto<List<DiscountDataDto>>(data,null), HttpStatus.OK);
        }catch(Exception e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getDiscountDropDownByUserId")
    public ResponseEntity<ResponseDto<List<DiscountDropDownDto>>> getDiscountDropDownByUserId(@RequestBody EntityIdWithTypeDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<DiscountDropDownDto> data = discountService.getDiscountDropDownByType(dto);
            return new ResponseEntity<ResponseDto<List<DiscountDropDownDto>>>(new ResponseDto<List<DiscountDropDownDto>>(data,null), HttpStatus.OK);
        }catch(Exception e){
            return errorHandler.handleError(e);
        }
    }
}
