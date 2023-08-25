package com.billingapp.payload.commonDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EntityIdAndValueDto {
    private Long entityId;
    private String entityValue;
}
