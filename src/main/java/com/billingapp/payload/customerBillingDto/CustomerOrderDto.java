package com.billingapp.payload.customerBillingDto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerOrderDto {
    private Long customerId;
    private String customerName;
    private Long customerMobile;
    private String customerEmail;
    private String village;
    private Double orderTotalAmt;
    private Double orderDiscount;
    private Integer orderDiscountType;
    private Double orderDiscountedAmt;
    private List<CustomerOrderItemDto> itemDtoList;
    private Long requestedById;
    private Integer requestedBy;
}
