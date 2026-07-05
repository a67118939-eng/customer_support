package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.HoneypotEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoneypotEventRepository extends JpaRepository<HoneypotEvent, Long> {

    List<HoneypotEvent> findAllByOrderByCreatedAtDesc();

    long countByBlockedTrue();
}