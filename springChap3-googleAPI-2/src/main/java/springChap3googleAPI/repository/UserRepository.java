package springChap3googleAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springChap3googleAPI.model.UserGoogle;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserGoogle, Long> {
    Optional<UserGoogle> findByUsername(String username);
}