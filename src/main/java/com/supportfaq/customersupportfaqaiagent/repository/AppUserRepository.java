package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmailIgnoreCase(String email);

    List<AppUser> findAllByRoleIgnoreCase(String role);

    boolean existsByEmailIgnoreCase(String email);
}
