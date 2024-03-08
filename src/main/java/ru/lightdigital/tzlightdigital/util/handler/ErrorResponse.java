package ru.lightdigital.tzlightdigital.util.handler;

import lombok.*;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final String title;
    private final Integer status_code;
    private final String desc;
}
