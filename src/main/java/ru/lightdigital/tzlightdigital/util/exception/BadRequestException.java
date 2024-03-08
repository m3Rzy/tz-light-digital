package ru.lightdigital.tzlightdigital.util.exception;

import lombok.*;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
    private String desc;
}
