package ru.lightdigital.tzlightdigital.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class UserDtoOutput {
    private String name;
    private String phone;
}
