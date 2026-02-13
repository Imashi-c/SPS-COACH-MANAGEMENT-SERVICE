package com.sps.coach.repository;

import com.sps.coach.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Coach entity
 * Provides database operations for Coach management
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    /**
     * Find coach by email address
     * @param email the email to search for
     * @return Optional containing the coach if found
     */
    Optional<Coach> findByEmail(String email);

    /**
     * Find coaches by specialization
     * @param specialization the specialization to search for
     * @return List of coaches with the given specialization
     */
    List<Coach> findBySpecialization(String specialization);

    /**
     * Search coaches by name (case-insensitive, partial match)
     * @param name the name to search for
     * @return List of matching coaches
     */
    List<Coach> findByNameContainingIgnoreCase(String name);

    /**
     * Check if email already exists
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Custom query to find coaches by specialization with custom message
     * @param specialization the specialization to search for
     * @return List of coaches
     */
    @Query("SELECT c FROM Coach c WHERE LOWER(c.specialization) = LOWER(:specialization)")
    List<Coach> findCoachesBySpecialization(@Param("specialization") String specialization);

    /**
     * Count total number of coaches
     * @return total count of coaches
     */
    @Query("SELECT COUNT(c) FROM Coach c")
    long countTotalCoaches();
}
