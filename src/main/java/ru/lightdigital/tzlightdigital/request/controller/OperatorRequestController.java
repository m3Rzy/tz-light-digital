package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.dto.RequestDtoInput;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.service.RequestService;

import java.util.List;

import static ru.lightdigital.tzlightdigital.request.model.StatusRequest.SENT;

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
        return requestService.acceptOrRejectRequest(id, requestDtoInput);
    }

    @GetMapping("/sort")
    @PreAuthorize("hasAuthority('ROLE_OPERATOR')")
    public List<Request> readRequestsBySortWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", required = false) String sortDirection) {
        return requestService.getAllBySortWithPagination(page, size, sortDirection)
                .stream()
                .filter(i -> i.getStatusRequest().equals(SENT)).toList();
    }

    @GetMapping("/name")
    @PreAuthorize("hasAuthority('ROLE_OPERATOR')")
    public List<Request> readRequestsByFilterUsernameWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam String name) {
        return requestService.getAllByFilterUsernameWithPagination(page, size, name)
                .stream()
                .filter(i -> i.getStatusRequest().equals(SENT)).toList();
    }
}
