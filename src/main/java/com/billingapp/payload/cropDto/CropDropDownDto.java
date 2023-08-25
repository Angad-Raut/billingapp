package com.billingapp.payload.cropDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CropDropDownDto {
    private Long cropId;
    private String cropName;
    private Double cropPrice;
}
