package ru.lightdigital.tzlightdigital.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoOutput {
    private String phone;
    private String password;
    private String token;
}
