package com.sps.coach.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Coach
 * Used for API requests and responses
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoachDTO {

    private Long coachId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(max = 100, message = "Specialization must not exceed 100 characters")
    private String specialization;

    @Size(max = 15, message = "Phone must not exceed 15 characters")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor without timestamps (for requests)
     */
    public CoachDTO(String name, String specialization, String phone, String email) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }
}
