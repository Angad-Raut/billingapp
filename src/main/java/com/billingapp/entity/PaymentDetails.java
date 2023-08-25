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
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Integer paymentStatus;
    private Double totalAmount;
    private Double paidAmount;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "paymentDetails")
    private CustomerOrderDetails customerOrderDetails;
    private Long insertedById;
    private Integer insertedBy;
    private Date insertedTime;
    private Date updatedTime;
    private Long updatedById;
    private Integer updatedBy;
}
