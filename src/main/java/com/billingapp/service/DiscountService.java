package com.billingapp.service;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.EntityIdWithTypeDto;
import com.billingapp.payload.discountDto.DiscountDataDto;
import com.billingapp.payload.discountDto.DiscountDropDownDto;
import com.billingapp.payload.discountDto.DiscountDto;
import com.billingapp.payload.discountDto.DiscountTypeDto;

import java.util.List;

public interface DiscountService {
    Boolean addDiscount(DiscountDto dto)throws AlreadyExistException, InvalidDataException;
    DiscountDataDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    List<DiscountDataDto> getAllDiscountsByUserId(EntityIdWithTypeDto dto)throws ResourceNotFoundException;
    List<DiscountTypeDto> getDiscountDropDown();
    List<DiscountDropDownDto> getDiscountDropDownByType(EntityIdWithTypeDto dto);
}
