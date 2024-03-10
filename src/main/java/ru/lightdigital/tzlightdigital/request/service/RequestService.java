package ru.lightdigital.tzlightdigital.request.service;

import org.springframework.data.domain.Page;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;

import java.util.List;

public interface RequestService {

    List<Request> getAllByFilterStatusWithPagination(int page, int size, StatusRequest statusRequest);

    List<Request> getAllByFilterUsernameWithPagination(int page, int size, String username);

    Page<Request> getAllBySortWithPagination(int page, int size, String sortDirection);

    Request getById(Long id);

    Request add(Long userId, Request request);

    void delete(Long id);

    Request patchRequest(Long id, Request request);
}
