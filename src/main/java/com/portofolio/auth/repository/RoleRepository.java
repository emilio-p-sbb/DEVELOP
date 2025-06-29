package com.portofolio.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portofolio.auth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
    Optional<Role> findByRoleName(String roleName);
    List<Role> findByRoleNameIn(List<String> roleNames);
    
}
