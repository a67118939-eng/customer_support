package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {

    List<Faq> findByStatusIgnoreCase(String status);

    List<Faq> findBySourceIgnoreCaseOrderByUpdatedAtDesc(String source);

    List<Faq> findBySourceIgnoreCaseAndReviewedFalseOrderByUpdatedAtDesc(String source);

    List<Faq> findAllByOrderByUpdatedAtDesc();

    List<Faq> findByCategoryIgnoreCase(String category);

    List<Faq> findByLanguageIgnoreCase(String language);

    List<Faq> findByStatusIgnoreCaseAndLanguageIgnoreCase(String status, String language);

    List<Faq> findByStatusIgnoreCaseAndCategoryIgnoreCase(String status, String category);

    List<Faq> findByStatusIgnoreCaseAndCategoryIgnoreCaseAndLanguageIgnoreCase(String status, String category, String language);

    List<Faq> findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCaseOrKeywordsContainingIgnoreCase(
            String question, String answer, String keywords);

    long countByStatusIgnoreCase(String status);

    boolean existsByQuestionIgnoreCase(String question);
}
