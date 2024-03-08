package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class RequestController {

    //todo: подумать насчет заголовков, мб заменить на параметры
    public static final String header = "X-Sharer-User-Id";
    private RequestService requestService;

    @GetMapping
    public List<Request> readRequests() {
        return requestService.getAll();
    }

    @GetMapping("/page")
    public Page<Request> readRequestsWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return requestService.getAllWithPagination(page, size);
    }

    @GetMapping("/{id}")
    public Request readRequest(@PathVariable Long id) {
        return requestService.getById(id);
    }

    @GetMapping("/sort")
    public List<Request> readRequestsBySort(@RequestParam(value = "sort", required = false) String sortDirection) {
        return requestService.getAllSorted(sortDirection);
    }

    @GetMapping("/filter")
    public List<Request> readRequestsByStatusFilter(@RequestParam StatusRequest status) {
            return requestService.getAllByFilter(status);
    }

    @PostMapping
    public Request saveRequest(@RequestHeader(header) Long userId, @RequestBody Request request) {
        return requestService.add(userId, request);
    }

    @PatchMapping
    public Request changeStatusRequest(@RequestHeader(header) Long userId,
                                       @RequestBody Request request) {
        return requestService.changeStatusRequest(userId, request);
    }
}
