package ru.lightdigital.tzlightdigital.request.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;
import ru.lightdigital.tzlightdigital.request.repository.RequestRepository;
import ru.lightdigital.tzlightdigital.user.service.UserService;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private UserService userService;

    @Override
    public List<Request> getAll() {
        log.info("Количество обращений: {}", requestRepository.findAll().size());
        return requestRepository.findAll();
    }

    @Override
    public List<Request> getAllByFilter(StatusRequest statusRequest) {
        log.info("Найдены обращения по фильтру {}", statusRequest);
        return requestRepository.findAll()
                .stream()
                .filter(i -> i.getStatusRequest().equals(statusRequest))
                .collect(Collectors.toList());
    }

    @Override
    public List<Request> getAllSorted(String sortDirection) {
        if (sortDirection != null) {
            sortDirection = sortDirection.toUpperCase();

            if (sortDirection.equals("DESC")) {
                log.info("Обращения отсортированы (сначала новые).");
                return requestRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            }
        }
        log.info("Обращения отсортированы (сначала старые).");
        return requestRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Обращения с id " + id + " не существует!"));
    }

    @Override
    public Request add(Long userId, Request request) {
        request.setStatusRequest(StatusRequest.SENT);
        request.setUser(userService.getById(userId));
        return requestRepository.save(request);
    }

    @Override
    public void delete(Long id) {
        requestRepository.delete(getById(id));
    }

    @Override
    public Request changeStatusRequest(Long id, Request request) {
        Request oldRequest = getById(id);
        oldRequest.setStatusRequest(request.getStatusRequest());
        oldRequest.setDescription(request.getDescription());
        requestRepository.saveAndFlush(oldRequest);
        log.info("{} успешно сменился.", oldRequest);
        return oldRequest;
    }
}
