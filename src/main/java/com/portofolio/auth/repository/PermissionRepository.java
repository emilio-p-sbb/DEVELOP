package com.portofolio.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portofolio.auth.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}