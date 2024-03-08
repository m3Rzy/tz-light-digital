package ru.lightdigital.tzlightdigital.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class UserDtoInput {
    private long id;
    private String name;
    private String phone;
    private String password;
}
