package com.billingapp.payload.jwtDto;

import com.billingapp.payload.loginDto.LoginResponseDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponseDto {
    private String jwtToken;
    private LoginResponseDto loginResponseDto;
}
