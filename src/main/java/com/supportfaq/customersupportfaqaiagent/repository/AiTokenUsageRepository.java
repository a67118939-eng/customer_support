package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.AiTokenUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AiTokenUsageRepository extends JpaRepository<AiTokenUsage, Long> {

    Optional<AiTokenUsage> findBySessionId(String sessionId);
}
