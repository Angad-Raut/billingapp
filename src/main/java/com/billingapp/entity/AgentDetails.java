package com.billingapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "agent_details")
public class AgentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentId;
    private String agentName;
    private Long agentMobile;
    private String agentEmail;
    private String agentAddress;
    private Boolean status;
    private Long insertedById;
    private Integer insertedBy;
    private Date insertedTime;
    private Long updatedById;
    private Integer updatedBy;
    private Date updatedTime;
}
