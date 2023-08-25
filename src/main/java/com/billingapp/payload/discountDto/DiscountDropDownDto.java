package com.billingapp.payload.discountDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiscountDropDownDto {
    private Long id;
    private Double discount;
}
