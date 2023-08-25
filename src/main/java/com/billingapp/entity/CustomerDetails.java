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
@Table(name = "customer_details")
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String customerName;
    private Long customerMobile;
    private String customerEmail;
    private String village;
    private Boolean status;
    private Long insertedById;
    private Integer insertedBy;
    private Date insertedTime;
    private Long updatedById;
    private Integer updatedBy;
    private Date updatedTime;
}
