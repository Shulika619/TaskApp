package dev.shulika.telegrambot.repository;

import dev.shulika.telegrambot.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}