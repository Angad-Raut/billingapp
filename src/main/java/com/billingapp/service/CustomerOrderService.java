package com.billingapp.service;

import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.customerBillingDto.*;

import java.util.List;

public interface CustomerOrderService {
    Boolean placeOrder(CustomerOrderDto dto)throws AlreadyExistException,InvalidDataException;
    Boolean doPayment(CustomerPaymentDto dto)throws InvalidDataException;
    CustomerOrderDataDto getOrdersDetailsByOrderId(EntityIdDto dto);
    List<CustomerOrderDetailsDto> getAllCustomersOrders(EntityIdDto dto);
    OrderPaymentDto getOrderAndPaymentDetails(EntityIdDto dto);
}
