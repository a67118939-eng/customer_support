package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    long countByAnsweredTrue();

    long countByAnsweredFalse();

    long countByLanguageIgnoreCase(String language);

    java.util.List<ChatHistory> findTop50ByOrderByCreatedAtDesc();

    @Query("select coalesce(avg(c.confidenceScore), 0) from ChatHistory c")
    double averageConfidenceScore();
}
