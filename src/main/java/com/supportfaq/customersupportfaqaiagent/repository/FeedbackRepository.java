package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByOrderByCreatedAtDesc();

    long countByHelpfulTrue();

    long countByHelpfulFalse();
}
