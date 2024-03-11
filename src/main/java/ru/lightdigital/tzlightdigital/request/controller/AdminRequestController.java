package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

import java.util.List;

import static ru.lightdigital.tzlightdigital.request.model.StatusRequest.DRAFT;

@RestController
@RequestMapping("/admin/request")
@AllArgsConstructor
public class AdminRequestController {
    private RequestService requestService;

//    сортировка по актуальности с пагинацией +
    @GetMapping("/sort")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Request> readRequestsBySortWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", required = false) String sortDirection) {
        return requestService.getAllBySortWithPagination(page, size, sortDirection)
                .stream()
                .filter(i -> !i.getStatusRequest().equals(DRAFT)).toList();
    }

//    фильтрация по статусу обращения с пагинацией
    @GetMapping("/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Request> readRequestsByFilterStatusWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam StatusRequest status) {
        return requestService.getAllByFilterStatusWithPagination(page, size, status);
    }

//    фильтрация по именам пользователя с пагинацией
    @GetMapping("/name")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Request> readRequestsByFilterUsernameWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam String name) {
        return requestService.getAllByFilterUsernameWithPagination(page, size, name);
    }
}
