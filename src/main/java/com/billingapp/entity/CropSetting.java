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
@Table(name = "crop_setting")
public class CropSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double warai;
    private Double hamali;
    private Double tollai;
    private Double bhade;
    private Boolean status;
    private Long insertedById;
    private Integer insertedBy;
    private Date insertedTime;
    private Long updatedById;
    private Integer updatedBy;
    private Date updatedTime;
}
