package com.billingapp.payload.customerBillingDto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerOrderDetailsDto {
    private Integer srNo;
    private Long orderId;
    private String orderNumber;
    private String customerName;
    private Long customerMobile;
    private String orderDate;
    private Double orderAmount;
    private Double discountedAmount;
}
