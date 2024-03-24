package by.betrayal.accountservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "links")
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Long createdAt;

    @Column(name = "expired_at")
    private Long expiredAt;
}
