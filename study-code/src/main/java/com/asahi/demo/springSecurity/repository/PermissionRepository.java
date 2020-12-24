package com.asahi.demo.springSecurity.repository;

import com.asahi.demo.springSecurity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
}
