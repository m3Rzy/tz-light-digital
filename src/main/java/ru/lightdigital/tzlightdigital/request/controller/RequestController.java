package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class RequestController {

    private RequestService requestService;

    @GetMapping
    public List<Request> readRequests() {
        return requestService.getAll();
    }

//    Сортировка от старого обращения к новому
    @GetMapping("/asc")
    public List<Request> readRequestsOrderByAsc() {
        return requestService.getAll()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

//    Сортировка от нового обращения к старому
    @GetMapping("/desc")
    public List<Request> readRequestsOrderByDesc() {
        return requestService.getAll()
                .stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

//    Фильтрация по статусу обращения
    @GetMapping("/{status}")
    public List<Request> readRequestsByStatusFilter(@PathVariable String status) {
            return requestService.getAll()
                    .stream()
                    .filter(i -> i.getStatusRequest().equals(StatusRequest.valueOf(status)))
                    .collect(Collectors.toList());
    }
}
