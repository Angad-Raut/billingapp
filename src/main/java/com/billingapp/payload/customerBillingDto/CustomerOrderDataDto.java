package com.billingapp.payload.customerBillingDto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerOrderDataDto {
    private String orderNumber;
    private String orderDate;
    private String customerName;
    private Long customerMobile;
    private String customerEmail;
    private String customerVillage;
    private Double orderAmount;
    private Double orderDiscount;
    private Double orderDiscountedAmount;
    private String paymentStatus;
    private List<OrderItemDto> orderItems;
}
