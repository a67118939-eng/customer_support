package com.supportfaq.customersupportfaqaiagent.repository;

import com.supportfaq.customersupportfaqaiagent.entity.BlockedIp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockedIpRepository extends JpaRepository<BlockedIp, Long> {

    Optional<BlockedIp> findFirstByIpAddressAndActiveTrueOrderByBlockedAtDesc(String ipAddress);

    List<BlockedIp> findAllByOrderByBlockedAtDesc();

    long countByActiveTrue();
}