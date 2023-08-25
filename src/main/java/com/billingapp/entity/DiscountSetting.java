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
@Table(name = "discount_setting")
public class DiscountSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer discountType;
    private Double discountValue;
    private Long insertedById;
    private Integer insertedBy;
    private Date insertedTime;
    private Long updatedById;
    private Integer updatedBy;
    private Date updatedTime;
}
