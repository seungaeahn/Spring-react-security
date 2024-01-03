package springChap3googleAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import springChap3googleAPI.model.UserGoogle;

public interface UserRepository extends JpaRepository<UserGoogle, Long> {
    Optional<UserGoogle> findByUsername(String username);
}
