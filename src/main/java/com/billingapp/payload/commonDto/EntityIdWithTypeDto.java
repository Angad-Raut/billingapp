package com.billingapp.payload.commonDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EntityIdWithTypeDto {
    private Long entityId;
    private Integer entityType;
}
