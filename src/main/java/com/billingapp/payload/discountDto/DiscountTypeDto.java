package com.billingapp.payload.discountDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiscountTypeDto {
    private Integer discountType;
    private String discountName;
}
