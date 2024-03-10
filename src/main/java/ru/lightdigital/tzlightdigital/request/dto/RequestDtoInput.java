package ru.lightdigital.tzlightdigital.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDtoInput {
    private StatusRequest statusRequest;
}
