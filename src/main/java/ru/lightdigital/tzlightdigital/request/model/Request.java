package ru.lightdigital.tzlightdigital.request.model;

import jakarta.persistence.*;
import lombok.*;
import ru.lightdigital.tzlightdigital.user.model.User;

@Entity
@Table(name = "requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "{Статус=" + statusRequest.getTitle() +
                ", текст='" + description + '\'' +
                ", тмя отправителя=" + user.getName() +
                ", номер отправителя=" + user.getPhone() +
                '}';
    }
}
