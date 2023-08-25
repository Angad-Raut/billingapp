package com.billingapp.payload.agentDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AgentDetailsDto {
    private Long agentId;
    private String agentName;
    private Long agentMobile;
    private String agentEmail;
    private String agentAddress;
    private Long requestedById;
    private Integer requestedBy;
}
