package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findAllByOrderByCreatedAtDesc();

    List<AuditLog> findBySeverityIgnoreCaseOrderByCreatedAtDesc(String severity);

    List<AuditLog> findByEventTypeIgnoreCaseOrderByCreatedAtDesc(String eventType);

    long countBySeverityIgnoreCase(String severity);

    long countByEventTypeIgnoreCase(String eventType);
}
