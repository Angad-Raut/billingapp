package com.billingapp.payload.discountDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiscountDataDto {
    private Long id;
    private Integer discountType;
    private Double discountValue;
}
