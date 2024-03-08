package ru.lightdigital.tzlightdigital.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role")
    private String role;
}
