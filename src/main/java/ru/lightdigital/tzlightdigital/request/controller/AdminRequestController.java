package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/admin/request")
@AllArgsConstructor
public class AdminRequestController {
    private RequestService requestService;

//    сортировка по актуальности с пагинацией +
    @GetMapping("/sort")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<Request> readRequestsBySortWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", required = false) String sortDirection) {
        return requestService.getAllBySortWithPagination(page, size, sortDirection);
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
