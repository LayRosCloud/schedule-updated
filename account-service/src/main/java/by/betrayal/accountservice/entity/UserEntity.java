package by.betrayal.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private String password;

    @Column(name = "is_banned", nullable = false, columnDefinition = "false")
    private Boolean isBanned;

    @Column(name = "is_activated", nullable = false, columnDefinition = "false")
    private Boolean isActivated;

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;
}
