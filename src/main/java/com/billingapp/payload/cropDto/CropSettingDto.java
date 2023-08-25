package com.billingapp.payload.cropDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CropSettingDto {
    private Long id;
    private Double warai;
    private Double hamali;
    private Double tollai;
    private Double bhade;
    private Long requestedById;
    private Integer requestedBy;
}
