package com.billingapp.payload.cropDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CropDetailsDto {
    private Long cropId;
    private String cropName;
    private Double cropPrice;
    private Long requestedById;
    private Integer requestedBy;
}
