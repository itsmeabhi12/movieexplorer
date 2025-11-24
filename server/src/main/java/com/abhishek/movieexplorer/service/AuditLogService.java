package com.abhishek.movieexplorer.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.movieexplorer.context.RequestContext;
import com.abhishek.movieexplorer.model.AuditLog;
import com.abhishek.movieexplorer.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    final AuditLogRepository auditLogRepository;
    final ObjectMapper objectMapper;

    @Transactional
    public void logCreateAction(Object newObject, UUID actorId, AuditLog.TargetType targetType,
            String targetId) {
        AuditLog auditLog = base(AuditLog.Action.CREATE, actorId, targetType, targetId);
        auditLog.setNewValues(newObject != null ? serializeObject(newObject) : null);
        auditLog.setDiff(serializeObject(newObject));
        auditLogRepository.save(auditLog);
    }

    @Transactional
    public void logUpdateAction(Object previousObject, Object newObject, UUID actorId,
            AuditLog.TargetType targetType, String targetId, Map<String, Object> diff) {
        AuditLog auditLog = base(AuditLog.Action.UPDATE, actorId, targetType, targetId);
        auditLog.setPreviousValues(previousObject != null ? serializeObject(previousObject) : null);
        auditLog.setNewValues(newObject != null ? serializeObject(newObject) : null);
        auditLog.setDiff(diff != null ? serializeObject(diff) : null);

        auditLogRepository.save(auditLog);
    }

    @Transactional
    public void logDeleteAction(Object previousObject, UUID actorId, AuditLog.TargetType targetType,
            String targetId) {
        AuditLog auditLog = base(AuditLog.Action.DELETE, actorId, targetType, targetId);
        auditLog.setPreviousValues(previousObject != null ? serializeObject(previousObject) : null);
        auditLog.setDiff(null);
        auditLogRepository.save(auditLog);
    }

    private AuditLog base(AuditLog.Action action, UUID actorId, AuditLog.TargetType targetType, String targetId) {
        return AuditLog.builder()
                .action(action)
                .actorId(actorId)
                .requestId(RequestContext.get().getRequestId())
                .targetType(targetType)
                .targetId(targetId)
                .build();
    }

    private String serializeObject(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

}
