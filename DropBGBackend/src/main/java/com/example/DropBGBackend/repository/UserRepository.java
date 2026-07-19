package com.example.DropBGBackend.repository;

import com.example.DropBGBackend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByClerkId(String clerkId);
    boolean existsByClerkId(String clerkId);
}
