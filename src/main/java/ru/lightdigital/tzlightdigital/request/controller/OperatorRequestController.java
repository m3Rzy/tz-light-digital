package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.dto.RequestDtoInput;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

@RestController
@RequestMapping("/operator/request")
@AllArgsConstructor
public class OperatorRequestController {
    private RequestService requestService;

//    просмотр заявки по id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_OPERATOR')")
    public Request readRequestById(@PathVariable long id) {
        return requestService.getById(id);
    }

//    принять/отклонить заявку
    @PatchMapping
    @PreAuthorize("hasAuthority('ROLE_OPERATOR')")
    public Request changeStatusRequest(@RequestParam long id,
                                       @RequestBody RequestDtoInput requestDtoInput) {
        return requestService.changeStatus(id, requestDtoInput);
    }
}
