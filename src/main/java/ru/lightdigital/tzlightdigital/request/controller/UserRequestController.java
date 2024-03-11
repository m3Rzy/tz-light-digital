package ru.lightdigital.tzlightdigital.request.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.lightdigital.tzlightdigital.request.model.Request;
import ru.lightdigital.tzlightdigital.request.service.RequestService;
import ru.lightdigital.tzlightdigital.user.model.User;
import ru.lightdigital.tzlightdigital.user.repository.UserRepository;
import ru.lightdigital.tzlightdigital.util.exception.AccessException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/request")
@AllArgsConstructor
public class UserRequestController {
    public static final String requestHeader = "X-Sharer-Request-Id";
    private RequestService requestService;
    private UserRepository userRepository;

//    создать черновик
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Request saveRequest(@AuthenticationPrincipal Principal principal, @RequestBody Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String phone = userDetails.getUsername();

        Optional<User> userOptional = userRepository.findByPhone(phone);
        Long userId;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userId = user.getId();
        } else {
            throw new AccessException("Не удалось найти пользователя с номером: " + phone);
        }

        if (request.getUser() != null && !request.getUser().getId().equals(userId)) {
            throw new AccessException("Это не Ваше обращение, вы к нему не можете обращаться!");
        }

        return requestService.add(userId, request);
    }

//    отправить заявку
    @PatchMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Request updateRequest(@RequestHeader(requestHeader) Long requestId,
                               @RequestBody Request request, @AuthenticationPrincipal Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String phone = userDetails.getUsername();

        Optional<User> userOptional = userRepository.findByPhone(phone);

        Long userId;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userId = user.getId();
            if (requestService.getById(requestId).getUser().getId().equals(userId)) {
                return requestService.sendRequest(requestId, request);
            } else {
                throw new AccessException("Это не Ваше обращение, вы к нему не можете обращаться!");
            }
        } else {
            throw new AccessException("Не удалось найти пользователя с номером: " + phone);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Request> readRequestsBySortWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", required = false) String sortDirection,
            @AuthenticationPrincipal Principal principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String phone = userDetails.getUsername();

        Optional<User> userOptional = userRepository.findByPhone(phone);
        Long userId;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userId = user.getId();
        } else {
            throw new AccessException("Не удалось найти пользователя с номером: " + phone);
        }

        return requestService.getPersonalRequestsWithSortWithPagination(page, size, sortDirection, userId);
    }
}
