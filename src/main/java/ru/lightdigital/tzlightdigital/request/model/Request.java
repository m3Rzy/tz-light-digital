package ru.lightdigital.tzlightdigital.request.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "requests")
@Data
@AllArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_request", nullable = false)
    private StatusRequest statusRequest;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "user_name", nullable = false)
    private String userName;
}
