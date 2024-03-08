package ru.lightdigital.tzlightdigital.request.service;

import ru.lightdigital.tzlightdigital.request.model.Request;

import java.util.List;

public interface RequestService {

    List<Request> getAll();

    Request getById(Long id);

    Request add(Request request);

    void delete(Long id);
}
