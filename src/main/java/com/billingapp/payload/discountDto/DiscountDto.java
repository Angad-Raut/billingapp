package com.billingapp.payload.discountDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiscountDto {
    private Long id;
    private Integer discountType;
    private Double discountValue;
    private Long requestedById;
    private Integer requestedBy;
}
