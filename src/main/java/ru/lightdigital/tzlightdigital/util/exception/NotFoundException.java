package ru.lightdigital.tzlightdigital.util.exception;

import lombok.*;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String desc;
}
