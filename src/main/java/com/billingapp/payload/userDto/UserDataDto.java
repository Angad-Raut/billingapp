package com.billingapp.payload.userDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDataDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String email;
    private String shopName;
}
