package ru.lightdigital.tzlightdigital.request.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lightdigital.tzlightdigital.request.dto.RequestDtoInput;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.model.StatusRequest;
import ru.lightdigital.tzlightdigital.request.repository.RequestRepository;
import ru.lightdigital.tzlightdigital.user.service.UserService;
import ru.lightdigital.tzlightdigital.util.exception.AccessException;
import ru.lightdigital.tzlightdigital.util.exception.BadRequestException;
import ru.lightdigital.tzlightdigital.util.exception.NotFoundException;

import java.util.List;

import static ru.lightdigital.tzlightdigital.request.model.StatusRequest.*;

@Service
@AllArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private UserService userService;

    @Override
    public Page<Request> getAllBySortWithPagination(int page, int size, String sortDirection) {
        Pageable pageable;
        if (sortDirection != null) {
            if (sortDirection.equals("DESC")) {
                log.info("Обращения отсортированы (сначала новые).");
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            } else {
                log.info("Обращения отсортированы (сначала старые).");
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
            }
        } else {
            log.info("Обращения без сортировки.");
            pageable = PageRequest.of(page, size);
        }
        log.info("Используем пагинацию: {} страница, {} элементов.", page, size);
        return requestRepository.findAll(pageable);
    }

    @Override
    public List<Request> getAllByFilterStatusWithPagination(int page, int size, StatusRequest statusRequest) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Обращения отфильтрованы по {}.", statusRequest);
        log.info("Используем пагинацию: {} страница, {} элементов.", page, size);
        if (statusRequest != null) {
            if (statusRequest.equals(DRAFT)) {
                throw new AccessException("Нет доступа к просмотру черновиков у пользователей!");
            }
            if (statusRequest.equals(SENT) || statusRequest.equals(REJECTED) || statusRequest.equals(ACCEPTED)) {
                return requestRepository.findAll(pageable)
                        .stream()
                        .filter(i -> i.getStatusRequest().equals(statusRequest)).toList();
            }
            throw new BadRequestException("Такого статуса не существует!");
        }
        throw new BadRequestException("Статус не может быть пустым!");
    }

    @Override
    public List<Request> getAllByFilterUsernameWithPagination(int page, int size, String username) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Обращения отфильтрованы по {}.", username);
        log.info("Используем пагинацию: {} страница, {} элементов.", page, size);
        if (username != null) {
                return requestRepository.findAll(pageable)
                        .stream()
                        .filter(i -> i.getUser().getName().equals(username)).toList();
        }
        throw new BadRequestException("Имя не может быть пустым!");
    }

    @Override
    public Request getById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Обращения с id " + id + " не существует!"));
    }

    @Override
    public Request add(Long userId, Request request) {
        request.setStatusRequest(DRAFT);
        request.setUser(userService.getById(userId));
        log.info("Обращение {} успешно добавлено в список черновиков.", request);
        return requestRepository.save(request);
    }

    @Override
    public void delete(Long id) {
        log.info("Обращение с id {} успешно удалено!", id);
        requestRepository.delete(getById(id));
    }

    @Override
    public Request changeStatus(Long id, RequestDtoInput requestDtoInput) {
        Request oldRequest = getById(id);
        if (requestDtoInput.getStatusRequest() != null) {
            if (oldRequest.getStatusRequest().equals(SENT)) {
                switch (requestDtoInput.getStatusRequest()) {
                    case ACCEPTED -> oldRequest.setStatusRequest(ACCEPTED);
                    case REJECTED -> oldRequest.setStatusRequest(REJECTED);
                    default -> throw new BadRequestException("Обращение может быть принято или отклонено!");
                }
            }
            throw new BadRequestException("Некорректный статус обращения. " +
                    "Оператор может сменить статус только отправленным обращениям!");
        }
        throw new BadRequestException("Статус обращения не может быть пустым!");
    }

    @Override
    public Request patchRequest(Long id, Request request) {
        Request oldRequest = getById(id);
        if (oldRequest.getStatusRequest().equals(DRAFT)) {
            oldRequest.setDescription(request.getDescription());
            oldRequest.setStatusRequest(request.getStatusRequest());
            requestRepository.saveAndFlush(oldRequest);
            log.info("{} успешно сменился.", oldRequest);
            return oldRequest;
        } else {
            throw new BadRequestException("Нельзя изменить заявку, которая была отправлена, отклонена или принята!");
        }
    }
}
