package ru.lightdigital.tzlightdigital.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoInput {
    private String phone;
    private String password;
}
