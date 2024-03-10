package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class RequestController {

    public static final String header = "X-Sharer-User-Id";
    private RequestService requestService;

    @GetMapping("/{id}")
    public Request readRequest(@PathVariable Long id) {
        return requestService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Request saveRequest(@RequestHeader(header) Long userId, @RequestBody Request request) {
        return requestService.add(userId, request);
    }

    @PatchMapping
    public Request patchRequestWithStatusRequestDraft(@RequestHeader(header) Long userId,
                                       @RequestBody Request request) {
        return requestService.patchRequest(userId, request);
    }
}
