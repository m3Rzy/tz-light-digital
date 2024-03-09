package ru.lightdigital.tzlightdigital.user.service;

import ru.lightdigital.tzlightdigital.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(long id);

    User changeRole(long id);

}
