package com.billingapp.payload.customerBillingDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {
    private Integer srNo;
    private String cropName;
    private Double cropPrice;
    private Integer quantity;
    private Double weight;
    private Double totalAmount;
}
