package com.billingapp.payload.customerBillingDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderPaymentDto {
    private Long paymentId;
    private String orderNumber;
    private String orderDate;
    private Double orderAmount;
    private Double discountedAmount;
    private Double paidAmount;
    private Double unpaidAmount;
    private String paymentStatus;
}
