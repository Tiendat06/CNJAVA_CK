package com.java.repository;

import com.java.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM role r WHERE r.role_id = :roleId")
    Role findByRoleId(@Param("roleId") int roleId);

    @Query("SELECT r FROM role r WHERE r.role_name = :roleName")
    Role findRoleByRole_name(@Param("roleName") String roleName);
}
