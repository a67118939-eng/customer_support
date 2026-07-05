package com.supportfaq.customersupportfaqaiagent.service;

import com.supportfaq.customersupportfaqaiagent.entity.BlockedIp;
import com.supportfaq.customersupportfaqaiagent.exception.ResourceNotFoundException;
import com.supportfaq.customersupportfaqaiagent.repository.BlockedIpRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlockedIpService {

    private final BlockedIpRepository blockedIpRepository;
    private final AuditLogService auditLogService;
    private final InputSanitizer inputSanitizer;

    @Value("${app.security.honeypot.block-duration-hours:24}")
    private int blockDurationHours;

    public BlockedIpService(BlockedIpRepository blockedIpRepository,
                            AuditLogService auditLogService,
                            InputSanitizer inputSanitizer) {
        this.blockedIpRepository = blockedIpRepository;
        this.auditLogService = auditLogService;
        this.inputSanitizer = inputSanitizer;
    }

    public Optional<BlockedIp> getActiveBlock(String ipAddress) {
        Optional<BlockedIp> block = blockedIpRepository
                .findFirstByIpAddressAndActiveTrueOrderByBlockedAtDesc(ipAddress);

        if (block.isEmpty()) {
            return Optional.empty();
        }

        BlockedIp blockedIp = block.get();

        boolean permanent = Boolean.TRUE.equals(blockedIp.getPermanent());
        boolean expired = blockedIp.getExpiresAt() != null
                && LocalDateTime.now().isAfter(blockedIp.getExpiresAt());

        if (!permanent && expired) {
            blockedIp.setActive(false);
            blockedIpRepository.save(blockedIp);
            return Optional.empty();
        }

        return Optional.of(blockedIp);
    }

    public BlockedIp blockIp(String ipAddress, String reason, HttpServletRequest request) {
        String safeIpAddress = inputSanitizer.ipAddress(ipAddress);
        String safeReason = inputSanitizer.text(reason, 1000);
        BlockedIp blockedIp = new BlockedIp();
        blockedIp.setIpAddress(safeIpAddress);
        blockedIp.setReason(safeReason);
        blockedIp.setActive(true);
        blockedIp.setPermanent(false);
        blockedIp.setBlockedAt(LocalDateTime.now());
        blockedIp.setExpiresAt(LocalDateTime.now().plusHours(blockDurationHours));
        blockedIp.setBlockedBy("system");

        BlockedIp saved = blockedIpRepository.save(blockedIp);

        auditLogService.log(
                "IP_BLOCKED",
                "HIGH",
                "IP blocked: " + safeIpAddress + " Reason: " + safeReason,
                request,
                "BlockedIp",
                saved.getId()
        );

        return saved;
    }

    public BlockedIp manualBlock(String ipAddress, String reason, HttpServletRequest request) {
        return blockIp(ipAddress, reason, request);
    }

    public BlockedIp unblock(Long id, HttpServletRequest request) {
        BlockedIp blockedIp = blockedIpRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blocked IP not found"));

        blockedIp.setActive(false);
        BlockedIp saved = blockedIpRepository.save(blockedIp);

        auditLogService.log(
                "IP_UNBLOCKED",
                "MEDIUM",
                "IP unblocked: " + saved.getIpAddress(),
                request,
                "BlockedIp",
                saved.getId()
        );

        return saved;
    }

    public List<BlockedIp> getAll() {
        return blockedIpRepository.findAllByOrderByBlockedAtDesc();
    }

    public long countActive() {
        return blockedIpRepository.countByActiveTrue();
    }
    public long count() {
        return blockedIpRepository.count();
    }
}
