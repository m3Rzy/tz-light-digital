package ru.lightdigital.tzlightdigital.request.model;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum StatusRequest {
    DRAFT("Черновик"),
    SENT("Отправлено"),
    ACCEPTED("Принято"),
    REJECTED("Отклонено");

    private final String title;
}
