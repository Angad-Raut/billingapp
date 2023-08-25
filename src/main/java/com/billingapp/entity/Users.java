package com.billingapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "user_details")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Integer userType;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String email;
    private String shopName;
    private String password;
    private Boolean status;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="users_roles",
            joinColumns= {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> roles = new HashSet<>();
    private Long insertedById;
    private Integer insertedBy;
    private Date insertedTime;
    private Long updatedById;
    private Integer updatedBy;
    private Date updatedTime;
}
