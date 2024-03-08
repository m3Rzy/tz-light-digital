package ru.lightdigital.tzlightdigital.request.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.repository.RequestRepository;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;

    @Override
    public List<Request> getAll() {
        log.info("Количество обращений: {}", requestRepository.findAll().size());
        return requestRepository.findAll();
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Обращения с id " + id + " не существует!"));
    }

    @Override
    public Request add(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void delete(Long id) {
        requestRepository.delete(getById(id));
    }
}
