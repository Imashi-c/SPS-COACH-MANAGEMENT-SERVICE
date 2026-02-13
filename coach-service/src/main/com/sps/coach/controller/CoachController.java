package com.sps.coach.controller;

import com.sps.coach.dto.CoachDTO;
import com.sps.coach.service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Coach Management
 * Handles all HTTP requests for coach operations
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class CoachController {

    private final CoachService coachService;

    /**
     * Get all coaches
     * GET /api/coaches
     * @return List of all coaches
     */
    @GetMapping
    public ResponseEntity<List<CoachDTO>> getAllCoaches() {
        log.info("REST request to get all coaches");
        List<CoachDTO> coaches = coachService.getAllCoaches();
        return ResponseEntity.ok(coaches);
    }

    /**
     * Get coach by ID
     * GET /api/coaches/{id}
     * @param id the coach ID
     * @return Coach details
     */
    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> getCoachById(@PathVariable Long id) {
        log.info("REST request to get coach by id: {}", id);
        CoachDTO coach = coachService.getCoachById(id);
        return ResponseEntity.ok(coach);
    }

    /**
     * Create new coach
     * POST /api/coaches
     * @param coachDTO the coach data
     * @return Created coach
     */
    @PostMapping
    public ResponseEntity<CoachDTO> createCoach(@Valid @RequestBody CoachDTO coachDTO) {
        log.info("REST request to create new coach: {}", coachDTO.getName());
        CoachDTO createdCoach = coachService.createCoach(coachDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoach);
    }

    /**
     * Update existing coach
     * PUT /api/coaches/{id}
     * @param id the coach ID
     * @param coachDTO the updated coach data
     * @return Updated coach
     */
    @PutMapping("/{id}")
    public ResponseEntity<CoachDTO> updateCoach(
            @PathVariable Long id,
            @Valid @RequestBody CoachDTO coachDTO) {
        log.info("REST request to update coach with id: {}", id);
        CoachDTO updatedCoach = coachService.updateCoach(id, coachDTO);
        return ResponseEntity.ok(updatedCoach);
    }

    /**
     * Delete coach
     * DELETE /api/coaches/{id}
     * @param id the coach ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCoach(@PathVariable Long id) {
        log.info("REST request to delete coach with id: {}", id);
        coachService.deleteCoach(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Coach deleted successfully");
        response.put("id", id.toString());

        return ResponseEntity.ok(response);
    }

    /**
     * Search coaches by name
     * GET /api/coaches/search?name={name}
     * @param name the name to search for
     * @return List of matching coaches
     */
    @GetMapping("/search")
    public ResponseEntity<List<CoachDTO>> searchCoachesByName(@RequestParam String name) {
        log.info("REST request to search coaches by name: {}", name);
        List<CoachDTO> coaches = coachService.searchCoachesByName(name);
        return ResponseEntity.ok(coaches);
    }

    /**
     * Get coaches by specialization
     * GET /api/coaches/specialization/{specialization}
     * @param specialization the specialization to filter by
     * @return List of coaches
     */
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<CoachDTO>> getCoachesBySpecialization(
            @PathVariable String specialization) {
        log.info("REST request to get coaches by specialization: {}", specialization);
        List<CoachDTO> coaches = coachService.getCoachesBySpecialization(specialization);
        return ResponseEntity.ok(coaches);
    }

    /**
     * Get total coach count
     * GET /api/coaches/count
     * @return Total number of coaches
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getTotalCoachCount() {
        log.info("REST request to get total coach count");
        long count = coachService.getTotalCoachCount();

        Map<String, Long> response = new HashMap<>();
        response.put("totalCoaches", count);

        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     * GET /api/coaches/health
     * @return Service status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Coach Management Service");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}
