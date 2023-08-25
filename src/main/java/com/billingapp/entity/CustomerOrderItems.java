package com.billingapp.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Embeddable
public class CustomerOrderItems {
    private Long cropId;
    private Double rate;
    private Double weight;
    private Integer quantity;
    private Double totalAmount;
}
