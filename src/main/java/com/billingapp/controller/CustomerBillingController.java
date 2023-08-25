package com.billingapp.controller;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.commonDto.ResponseDto;
import com.billingapp.payload.customerBillingDto.*;
import com.billingapp.service.CustomerOrderService;
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
@RequestMapping(value = "/api/customerBilling")
public class CustomerBillingController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private ErrorHandlerComponent errorHandler;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/placeOrder")
    public ResponseEntity<ResponseDto<Boolean>> placeOrder(@RequestBody CustomerOrderDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = customerOrderService.placeOrder(dto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.CREATED);
        }catch (AlreadyExistException | InvalidDataException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/makePayment")
    public ResponseEntity<ResponseDto<Boolean>> makePayment(@RequestBody CustomerPaymentDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            Boolean data = customerOrderService.doPayment(dto);
            return new ResponseEntity<ResponseDto<Boolean>>(new ResponseDto<Boolean>(data,null), HttpStatus.CREATED);
        }catch (InvalidDataException e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getOrderDetailsByOrderId")
    public ResponseEntity<ResponseDto<CustomerOrderDataDto>> getOrderDetailsByOrderId(@RequestBody EntityIdDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            CustomerOrderDataDto data = customerOrderService.getOrdersDetailsByOrderId(dto);
            return new ResponseEntity<ResponseDto<CustomerOrderDataDto>>(new ResponseDto<CustomerOrderDataDto>(data,null), HttpStatus.CREATED);
        }catch (Exception e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getAllCustomersOrdersByUserId")
    public ResponseEntity<ResponseDto<List<CustomerOrderDetailsDto>>> getAllCustomersOrdersByUserId(@RequestBody EntityIdDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            List<CustomerOrderDetailsDto> data = customerOrderService.getAllCustomersOrders(dto);
            return new ResponseEntity<ResponseDto<List<CustomerOrderDetailsDto>>>(new ResponseDto<List<CustomerOrderDetailsDto>>(data,null), HttpStatus.CREATED);
        }catch (Exception e){
            return errorHandler.handleError(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/getOrderAndPaymentByOrderId")
    public ResponseEntity<ResponseDto<OrderPaymentDto>> getOrderAndPaymentByOrderId(@RequestBody EntityIdDto dto, BindingResult result){
        if(result.hasErrors()){
            return errorHandler.handleValidationErrors(result);
        }
        try{
            OrderPaymentDto data = customerOrderService.getOrderAndPaymentDetails(dto);
            return new ResponseEntity<ResponseDto<OrderPaymentDto>>(new ResponseDto<OrderPaymentDto>(data,null), HttpStatus.CREATED);
        }catch (Exception e){
            return errorHandler.handleError(e);
        }
    }
}
