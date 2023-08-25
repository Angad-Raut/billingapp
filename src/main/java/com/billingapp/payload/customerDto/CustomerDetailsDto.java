package com.billingapp.payload.customerDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerDetailsDto {
    private Long customerId;
    private String customerName;
    private Long customerMobile;
    private String customerEmail;
    private String village;
    private Long requestedById;
    private Integer requestedBy;
}
