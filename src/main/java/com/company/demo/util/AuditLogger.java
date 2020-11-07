package com.company.demo.util;

import com.company.demo.model.AuditLog;
import com.company.demo.model.Operation;
import com.company.demo.model.Resource;
import com.company.demo.model.User;
import com.company.demo.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditLogger {

    private static AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogger(AuditLogRepository auditLogRepository) {
        AuditLogger.auditLogRepository = auditLogRepository;
    }

    public static <T> void read(T entity) {
        AuditLog auditLog = null;

        if (entity instanceof User) {
            User user = (User) entity;
            auditLog = AuditLog.builder().resourceName(Resource.AUTH_USER).resourceId(user.getId())
                    .operatedAt(LocalDateTime.now()).operatedBy(1L).operation(Operation.READ).build();
        }

        if (auditLog != null) {
            auditLogRepository.save(auditLog);
        }
    }
}
