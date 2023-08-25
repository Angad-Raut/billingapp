package com.billingapp.payload.agentDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AgentDataDto {
    private Long agentId;
    private String agentName;
    private Long agentMobile;
    private String agentEmail;
    private String agentAddress;
    private String status;
}
