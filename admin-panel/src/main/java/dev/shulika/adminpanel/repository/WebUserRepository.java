package dev.shulika.adminpanel.repository;

import dev.shulika.adminpanel.model.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebUserRepository extends JpaRepository<WebUser, Long> {
    Optional<WebUser> findWebUserByUsername(String name);
}