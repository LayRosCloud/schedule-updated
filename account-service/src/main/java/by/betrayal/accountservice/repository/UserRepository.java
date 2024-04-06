package by.betrayal.accountservice.repository;

import by.betrayal.accountservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailOrLogin(String email, String login);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByLogin(String login);
}
