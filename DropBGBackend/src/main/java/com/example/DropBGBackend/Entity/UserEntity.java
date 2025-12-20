package com.example.DropBGBackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String clerkId;
    @Column(unique = true, nullable = false)
    private String email;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private Integer credits;

    @PrePersist
    public void prePersist() {
        if (credits == null) {
            credits = 50;
        }
    }
}
