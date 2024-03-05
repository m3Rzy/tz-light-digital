package ru.lightdigital.tzlightdigital.request.model;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum StatusRequest {
    DRAFT("Черновек"),
    SENT("Отправлено"),
    ACCEPTED("Принято"),
    REJECTED("Отклонено");

    private final String title;
}
