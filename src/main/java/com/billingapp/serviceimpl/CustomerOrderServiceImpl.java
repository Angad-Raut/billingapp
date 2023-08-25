package com.billingapp.serviceimpl;

import com.billingapp.entity.*;
import com.billingapp.exception.AlreadyExistException;
import com.billingapp.exception.InvalidDataException;
import com.billingapp.exception.ResourceNotFoundException;
import com.billingapp.payload.commonDto.EntityIdDto;
import com.billingapp.payload.customerBillingDto.*;
import com.billingapp.payload.customerDto.CustomerDataDto;
import com.billingapp.payload.customerDto.CustomerDetailsDto;
import com.billingapp.repository.CustomerOrderRepository;
import com.billingapp.repository.PaymentHistoryRepository;
import com.billingapp.repository.PaymentRepository;
import com.billingapp.service.CustomerOrderService;
import com.billingapp.service.CustomerService;
import com.billingapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepo;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private CustomerService customerService;

    private Double finalTotalAmt=0.0;

    @Transactional
    @Override
    public Boolean placeOrder(CustomerOrderDto dto) throws AlreadyExistException,InvalidDataException {
        CustomerOrderDetails orderDetails = null;
        if(dto.getCustomerId()==null){
            Long customerId = customerService.saveCustomer(setCustomerDto(dto));
            dto.setCustomerId(customerId);
        }
        if(dto.getOrderDiscount()!=null && dto.getOrderDiscountType()!=null){
            Double discountedAmount = calculateDiscount(dto.getOrderTotalAmt(),dto.getOrderDiscount(),dto.getOrderDiscountType());
            orderDetails = CustomerOrderDetails.builder()
                    .orderDiscount(dto.getOrderDiscount()!=null?dto.getOrderDiscount():null)
                    .orderDiscountType(dto.getOrderDiscountType()!=null?dto.getOrderDiscountType():null)
                    .customerId(dto.getCustomerId())
                    .customerOrderItems(setOrderItems(dto.getItemDtoList()))
                    .orderDiscountedAmt(discountedAmount)
                    .orderNumber(setOrderNumber())
                    .orderTotalAmt(finalTotalAmt)
                    .status(Constants.ACTIVE_STATUS)
                    .insertedById(dto.getRequestedById())
                    .insertedBy(dto.getRequestedBy())
                    .insertedTime(new Date())
                    .paymentDetails(setOrderPayment(discountedAmount,dto.getRequestedBy(),dto.getRequestedById()))
                    .build();
        }else{
            orderDetails = CustomerOrderDetails.builder()
                    .orderDiscount(dto.getOrderDiscount()!=null?dto.getOrderDiscount():null)
                    .orderDiscountType(dto.getOrderDiscountType()!=null?dto.getOrderDiscountType():null)
                    .customerId(dto.getCustomerId())
                    .customerOrderItems(setOrderItems(dto.getItemDtoList()))
                    .orderDiscountedAmt(finalTotalAmt)
                    .orderNumber(setOrderNumber())
                    .orderTotalAmt(finalTotalAmt)
                    .status(Constants.ACTIVE_STATUS)
                    .insertedById(dto.getRequestedById())
                    .insertedBy(dto.getRequestedBy())
                    .insertedTime(new Date())
                    .paymentDetails(setOrderPayment(dto.getOrderTotalAmt(),dto.getRequestedBy(),dto.getRequestedById()))
                    .build();
        }
        try{
            if(customerOrderRepo.save(orderDetails)!=null){
                return Constants.ACTIVE_STATUS;
            }else{
                return Constants.DI_ACTIVE_STATUS;
            }
        }catch (Exception e){
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean doPayment(CustomerPaymentDto dto) throws InvalidDataException {
        PaymentDetails paymentDetails = paymentRepository.getByPaymentId(dto.getPaymentId());
        if (paymentDetails==null) {
             throw new ResourceNotFoundException(Constants.CUSTOMER_PAYMENT_DETAILS_NOT_FOUND);
        } else if (paymentDetails.getPaidAmount().equals(paymentDetails.getTotalAmount())) {
             throw new AlreadyExistException(Constants.CUSTOMER_PAYMENT_ALREADY_DONE);
        } else {
             if(addPaymentHistory(dto.getPaymentId(),dto.getPaymentAmt(),dto.getRequestedById(),dto.getRequestedBy())) {
                 Integer paymentStatus = Constants.UNPAID;
                 Double paidAmount = (paymentDetails.getPaidAmount()+dto.getPaymentAmt());
                 if(paidAmount.equals(paymentDetails.getTotalAmount())){
                     paymentStatus = Constants.PAID;
                 }else{
                     paymentStatus = Constants.PARTIALLY_PAID;
                 }
                 Integer count = paymentRepository.updatePayment(paymentDetails.getPaymentId(),
                         paidAmount,paymentStatus,dto.getRequestedById(),dto.getRequestedBy());
                 if(count==1){
                     return Constants.ACTIVE_STATUS;
                 }else{
                     return Constants.DI_ACTIVE_STATUS;
                 }
             }else{
                 return Constants.DI_ACTIVE_STATUS;
             }
        }
    }

    @Override
    public CustomerOrderDataDto getOrdersDetailsByOrderId(EntityIdDto dto) {
        CustomerOrderDataDto convertedData = new CustomerOrderDataDto();
        List<Object[]> list = customerOrderRepo.getOrderDetailsByOrderId(dto.getEntityId());
        if(list!=null && !list.isEmpty()){
            for(Object[] objects:list){
                Integer discountType = (objects[6]!=null?Integer.parseInt(objects[6].toString()):null);
                Double discount = (objects[7]!=null?Double.parseDouble(objects[7].toString()):null);
                Double orderAmount = (objects[5]!=null?Double.parseDouble(objects[5].toString()):0.0);
                convertedData.setOrderNumber(objects[0]!=null?objects[0].toString():Constants.DASH);
                convertedData.setCustomerName(objects[1]!=null?objects[1].toString():Constants.DASH);
                convertedData.setCustomerMobile(objects[2]!=null?Long.parseLong(objects[2].toString()):null);
                convertedData.setCustomerEmail(objects[3]!=null?objects[3].toString():Constants.DASH);
                convertedData.setCustomerVillage(objects[4]!=null?objects[4].toString():Constants.DASH);
                convertedData.setOrderAmount(objects[5]!=null?Double.parseDouble(objects[5].toString()):0.0);
                if(discountType!=null && discount!=null && discountType.equals(Constants.PERCENTAGE)) {
                    convertedData.setOrderDiscount(setDiscount(orderAmount, discount));
                } else if (discountType!=null && discount!=null && discount.equals(Constants.RUPEES)) {
                    convertedData.setOrderDiscount(discount);
                } else {
                    convertedData.setOrderDiscount(0.0);
                }
                convertedData.setOrderDiscountedAmount(objects[8]!=null?Double.parseDouble(objects[8].toString()):0.0);
                convertedData.setOrderDate(objects[9]!=null?Constants.formatedDate(objects[9].toString()):Constants.DASH);
                convertedData.setOrderItems(setOrderItem(dto.getEntityId()));
                convertedData.setPaymentStatus(objects[10]!=null?Constants.setPaymentStatus(Integer.parseInt(objects[10].toString())):Constants.DASH);
            }
        }
        return convertedData;
    }

    @Override
    public List<CustomerOrderDetailsDto> getAllCustomersOrders(EntityIdDto dto) {
        List<CustomerOrderDetailsDto> convertedList = new ArrayList<CustomerOrderDetailsDto>();
        List<Object[]> list = customerOrderRepo.getAllCustomersOrdersByUserId(dto.getEntityId());
        Integer index=1;
        if(!list.isEmpty()){
            for (Object[] objects:list) {
                CustomerOrderDetailsDto response = new CustomerOrderDetailsDto();
                response.setSrNo(index);
                response.setOrderId(objects[0]!=null?Long.parseLong(objects[0].toString()):null);
                response.setOrderNumber(objects[1]!=null?objects[1].toString():"-");
                response.setOrderDate(objects[4]!=null? Constants.formatedDate(objects[4].toString()) :"-");
                response.setOrderAmount(objects[5]!=null?Double.parseDouble(objects[5].toString()):0.0);
                response.setCustomerName(objects[2]!=null?objects[2].toString():"-");
                response.setCustomerMobile(objects[3]!=null?Long.parseLong(objects[3].toString()):null);
                response.setDiscountedAmount(objects[6]!=null?Double.parseDouble(objects[6].toString()):0.0);
                convertedList.add(response);
                index++;
            }
        }
        return convertedList;
    }

    @Override
    public OrderPaymentDto getOrderAndPaymentDetails(EntityIdDto dto) {
        List<Object[]> list = customerOrderRepo.getOrderAndPaymentDetails(dto.getEntityId());
        if(list!=null && !list.isEmpty()){
             for(Object[] objects:list) {
                  Double discountedAmt = (objects[4]!=null?Double.parseDouble(objects[4].toString()):0.0);
                  Double paidAmt = (objects[5]!=null?Double.parseDouble(objects[5].toString()):0.0);
                  return OrderPaymentDto.builder()
                          .paymentId(objects[0]!=null?Long.parseLong(objects[0].toString()):null)
                          .orderNumber(objects[1]!=null?objects[1].toString():Constants.DASH)
                          .orderDate(objects[2]!=null?Constants.formatedDate(objects[2].toString()):Constants.DASH)
                          .orderAmount(objects[3]!=null?Double.parseDouble(objects[3].toString()):0.0)
                          .discountedAmount(discountedAmt)
                          .paidAmount(paidAmt)
                          .unpaidAmount(discountedAmt-paidAmt)
                          .paymentStatus(objects[6]!=null?Constants.setPaymentStatus(Integer.parseInt(objects[6].toString())):Constants.DASH)
                          .build();
             }
        }
        return new OrderPaymentDto();
    }

    private List<OrderItemDto> setOrderItem(Long orderId){
        List<OrderItemDto> orderItemDtoList = new ArrayList<OrderItemDto>();
        List<Object[]> list = customerOrderRepo.getOrderItems(orderId);
        Integer index=1;
        if(list!=null && !list.isEmpty()) {
            for(Object[] objects:list){
                OrderItemDto dto = new OrderItemDto();
                dto.setSrNo(index);
                dto.setCropName(objects[0]!=null?objects[0].toString():Constants.DASH);
                dto.setCropPrice(objects[1]!=null?Double.parseDouble(objects[1].toString()):0.0);
                dto.setQuantity(objects[2]!=null?Integer.parseInt(objects[2].toString()):0);
                dto.setWeight(objects[3]!=null?Double.parseDouble(objects[3].toString()):0.0);
                dto.setTotalAmount(objects[4]!=null?Double.parseDouble(objects[4].toString()):0.0);
                orderItemDtoList.add(dto);
                index++;
            }
        }
        return orderItemDtoList;
    }

    private List<CustomerOrderItems> setOrderItems(List<CustomerOrderItemDto> list){
        List<CustomerOrderItems> convertedList = new ArrayList<CustomerOrderItems>();
        for (CustomerOrderItemDto data: list) {
            convertedList.add(new CustomerOrderItems(data.getCropId(),
                    data.getRate(),data.getWeight(),data.getQuantity(),
                    data.getTotalAmount()));
            finalTotalAmt=finalTotalAmt+data.getTotalAmount();
        }
        return convertedList;
    }
    private PaymentDetails setOrderPayment(Double orderAmt,Integer type,Long userId){
        return PaymentDetails.builder()
                .paymentStatus(Constants.UNPAID)
                .totalAmount(orderAmt)
                .paidAmount(0.0)
                .insertedById(userId)
                .insertedBy(type)
                .insertedTime(new Date())
                .updatedTime(new Date())
                .updatedById(userId)
                .updatedBy(type)
                .build();
    }
    private CustomerDetailsDto setCustomerDto(CustomerOrderDto dto){
        return CustomerDetailsDto.builder().customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail()!=null?dto.getCustomerEmail():null).customerMobile(dto.getCustomerMobile())
                .village(dto.getVillage()).requestedById(dto.getRequestedById())
                .requestedBy(dto.getRequestedBy()).build();
    }
    private Double calculateDiscount(Double orderTotal,Double discount,Integer discountType){
        Double discountedAmt=0.0;
        if (discountType.equals(Constants.PERCENTAGE)){
            Double discountAmt = ((orderTotal*discount)/100);
            discountedAmt = (orderTotal-discountAmt);
        } else if(discountType.equals(Constants.RUPEES)){
            discountedAmt = (orderTotal-discount);
        }
        return discountedAmt;
    }
    private String setOrderNumber(){
        String orderNumber = customerOrderRepo.getLastOrderNumber();
        if (orderNumber!=null){
            long longValue = Long.parseLong(orderNumber);
            return String.valueOf(Long.valueOf(longValue+1));
        } else {
            return String.valueOf(Long.valueOf(1000000));
        }
    }
    private Boolean addPaymentHistory(Long paymentId,Double paymentAmt,Long userId,Integer type){
        PaymentHistory paymentHistory = PaymentHistory.builder()
                .paymentId(paymentId)
                .paidAmount(paymentAmt)
                .transactionId(UUID.randomUUID().toString())
                .insertedById(userId)
                .insertedBy(type)
                .insertedTime(new Date())
                .build();
        if(paymentHistoryRepository.save(paymentHistory)!=null){
            return Constants.ACTIVE_STATUS;
        }else{
            return Constants.DI_ACTIVE_STATUS;
        }
    }
    private Double setDiscount(Double orderTotal,Double discount){
        return ((orderTotal*discount)/100);
    }
}
