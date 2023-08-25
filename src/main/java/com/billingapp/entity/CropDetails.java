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
@Table(name = "crop_details")
public class CropDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropId;
    private String cropName;
    private Double cropPrice;
    private Boolean status;
    private Date insertedTime;
    private Long insertedId;
    private Integer insertedBy;
    private Date updatedTime;
    private Long updatedId;
    private Integer updatedType;
}
