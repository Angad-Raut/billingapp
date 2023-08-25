package com.billingapp.payload.customerBillingDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerPaymentDto {
    private Long orderId;
    private Long paymentId;
    private Double paymentAmt;
    private Long requestedById;
    private Integer requestedBy;
}
