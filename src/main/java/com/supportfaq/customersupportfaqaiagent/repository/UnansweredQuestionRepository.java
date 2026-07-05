package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.UnansweredQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnansweredQuestionRepository extends JpaRepository<UnansweredQuestion, Long> {

    List<UnansweredQuestion> findAllByOrderByCreatedAtDesc();

    List<UnansweredQuestion> findByStatusIgnoreCase(String status);

    long countByStatusIgnoreCase(String status);
}
