package com.reconcilex.repository;

import com.reconcilex.entity.UserRole;
import com.reconcilex.entity.UserRoleId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository
        extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findByUser_Id(Long userId);

    List<UserRole> findByRole_Id(Long roleId);

    boolean existsByUser_IdAndRole_Id(Long userId, Long roleId);
}