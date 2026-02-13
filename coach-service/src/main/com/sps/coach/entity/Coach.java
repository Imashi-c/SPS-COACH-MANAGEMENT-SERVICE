package com.sps.coach.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Coach Entity Class
 * Represents a coach in the cricket club
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Entity
@Table(name = "coaches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id")
    private Long coachId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 100, message = "Specialization must not exceed 100 characters")
    @Column(length = 100)
    private String specialization;

    @Size(max = 15, message = "Phone must not exceed 15 characters")
    @Column(length = 15)
    private String phone;

    @Email(message = "Email should be valid")
    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Set creation timestamp before persisting
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Update timestamp before updating
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Constructor for creating new coach without ID
     */
    public Coach(String name, String specialization, String phone, String email) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }
}
