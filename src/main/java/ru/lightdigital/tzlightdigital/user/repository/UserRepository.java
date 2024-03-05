package ru.lightdigital.tzlightdigital.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lightdigital.tzlightdigital.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
