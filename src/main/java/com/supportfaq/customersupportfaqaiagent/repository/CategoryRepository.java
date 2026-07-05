package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByCreatedAtDesc();

    List<Category> findByStatusIgnoreCase(String status);

    boolean existsByNameEnglishIgnoreCase(String nameEnglish);

    Optional<Category> findByNameEnglishIgnoreCase(String nameEnglish);
}
