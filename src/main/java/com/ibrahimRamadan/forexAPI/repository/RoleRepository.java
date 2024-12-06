package com.ibrahimRamadan.forexAPI.repository;

import com.ibrahimRamadan.forexAPI.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}
