package com.company.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "auth_audit_log")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Resource resourceName;
    private Long resourceId;
    @Enumerated(EnumType.STRING)
    private Operation operation;
    private String field;
    private String previousNewVal;
    private LocalDateTime operatedAt;
    private Long operatedBy;
}
