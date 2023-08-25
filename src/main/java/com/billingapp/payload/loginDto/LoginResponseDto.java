package com.billingapp.payload.loginDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginResponseDto {
    private Long userId;
    private Integer userType;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String email;
    private String shopName;
}
