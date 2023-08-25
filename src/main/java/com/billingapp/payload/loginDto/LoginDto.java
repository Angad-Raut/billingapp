package com.billingapp.payload.loginDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginDto {
    private Boolean isMobile;
    private String userName;
    private String password;
}
