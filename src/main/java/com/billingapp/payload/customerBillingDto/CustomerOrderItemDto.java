package com.billingapp.payload.customerBillingDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerOrderItemDto {
    private Long cropId;
    private Double rate;
    private Double weight;
    private Integer quantity;
    private Double totalAmount;
}
