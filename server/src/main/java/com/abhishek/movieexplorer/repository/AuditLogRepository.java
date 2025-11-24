package com.abhishek.movieexplorer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhishek.movieexplorer.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

}
