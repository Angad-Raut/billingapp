package com.billingapp.service;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.customerDto.CustomerDataDto;
import com.billingapp.payload.customerDto.CustomerDetailsDto;
import com.billingapp.payload.customerDto.SearchCustomerDataDto;

import java.util.List;

public interface CustomerService {
    Boolean addCustomer(CustomerDetailsDto dto)throws AlreadyExistException, InvalidDataException;
    CustomerDataDto getById(EntityIdDto dto)throws ResourceNotFoundException;
    Boolean updateStatus(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    List<CustomerDataDto> getAllCustomersByUserId(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    Long saveCustomer(CustomerDetailsDto dto)throws AlreadyExistException, InvalidDataException;
    SearchCustomerDataDto getCustomerDetailsByMobile(EntityIdDto dto)throws ResourceNotFoundException;
}
