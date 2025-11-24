package com.abhishek.movieexplorer.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "audit_logs")
@AllArgsConstructor
@Data
@Builder
public class AuditLog {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    @Column(nullable = false)
    private String requestId;

    @Column(nullable = false)
    private UUID actorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "", nullable = false)
    private TargetType targetType;

    @Column(nullable = false)
    private String targetId;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String previousValues;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String newValues;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String diff;

    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @Column(nullable = false)
    Instant updatedAt;

    @PrePersist
    public void onCreate() {
        this.updatedAt = Instant.now();
        this.createdAt = Instant.now();
    }

    @PostPersist
    public void onPostCreate() {
        this.createdAt = Instant.now();
    }

    public enum Action {
        CREATE,
        UPDATE,
        DELETE,
    }

    public enum TargetType {
        WATCHLIST_ITEM,
    }

}
