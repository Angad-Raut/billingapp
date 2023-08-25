package com.billingapp.repository;

import com.billingapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(value = "select * from roles where role_name=:role",nativeQuery = true)
    Role getByRoleName(@Param("role")String role);
}
