package com.billingapp.payload.userDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChangePasswordDto {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
