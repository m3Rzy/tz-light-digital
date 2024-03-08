package ru.lightdigital.tzlightdigital.request.service;

import org.springframework.data.domain.Page;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;

import java.util.List;

public interface RequestService {

    List<Request> getAll();

    List<Request> getAllByFilter(StatusRequest statusRequest);

    List<Request> getAllSorted(String sortDirection);

    Page<Request> getAllWithPagination(int page, int size);

    Request getById(Long id);

    Request add(Long userId, Request request);

    void delete(Long id);

    Request changeStatusRequest(Long id, Request request);
}
