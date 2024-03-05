package ru.lightdigital.tzlightdigital.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lightdigital.tzlightdigital.request.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}
