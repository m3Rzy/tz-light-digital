package ru.lightdigital.tzlightdigital.util.exception;

import lombok.*;

@Getter
@AllArgsConstructor
public class AccessException extends RuntimeException {
    private String desc;
}
