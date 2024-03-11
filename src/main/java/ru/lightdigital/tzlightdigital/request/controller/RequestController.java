package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class RequestController {

    private RequestService requestService;

    @GetMapping("/{id}")
    public Request readRequest(@PathVariable Long id) {
        return requestService.getById(id);
    }

}
