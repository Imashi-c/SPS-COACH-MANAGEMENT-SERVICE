package com.sps.coach.service;

import com.sps.coach.dto.CoachDTO;
import com.sps.coach.entity.Coach;
import com.sps.coach.exception.ResourceNotFoundException;
import com.sps.coach.exception.DuplicateResourceException;
import com.sps.coach.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Coach Management
 * Contains business logic for coach operations
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CoachService {

    private final CoachRepository coachRepository;

    /**
     * Get all coaches
     * @return List of all coaches
     */
    @Transactional(readOnly = true)
    public List<CoachDTO> getAllCoaches() {
        log.info("Fetching all coaches");
        return coachRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get coach by ID
     * @param id the coach ID
     * @return CoachDTO
     * @throws ResourceNotFoundException if coach not found
     */
    @Transactional(readOnly = true)
    public CoachDTO getCoachById(Long id) {
        log.info("Fetching coach with id: {}", id);
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found with id: " + id));
        return convertToDTO(coach);
    }

    /**
     * Create new coach
     * @param coachDTO the coach data
     * @return created CoachDTO
     * @throws DuplicateResourceException if email already exists
     */
    public CoachDTO createCoach(CoachDTO coachDTO) {
        log.info("Creating new coach: {}", coachDTO.getName());

        // Check if email already exists
        if (coachDTO.getEmail() != null && coachRepository.existsByEmail(coachDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + coachDTO.getEmail());
        }

        Coach coach = convertToEntity(coachDTO);
        Coach savedCoach = coachRepository.save(coach);
        log.info("Coach created successfully with id: {}", savedCoach.getCoachId());
        return convertToDTO(savedCoach);
    }

    /**
     * Update existing coach
     * @param id the coach ID
     * @param coachDTO the updated coach data
     * @return updated CoachDTO
     * @throws ResourceNotFoundException if coach not found
     * @throws DuplicateResourceException if email already exists for another coach
     */
    public CoachDTO updateCoach(Long id, CoachDTO coachDTO) {
        log.info("Updating coach with id: {}", id);

        Coach existingCoach = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found with id: " + id));

        // Check if email is being changed and if new email already exists
        if (coachDTO.getEmail() != null &&
                !coachDTO.getEmail().equals(existingCoach.getEmail()) &&
                coachRepository.existsByEmail(coachDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + coachDTO.getEmail());
        }

        existingCoach.setName(coachDTO.getName());
        existingCoach.setSpecialization(coachDTO.getSpecialization());
        existingCoach.setPhone(coachDTO.getPhone());
        existingCoach.setEmail(coachDTO.getEmail());

        Coach updatedCoach = coachRepository.save(existingCoach);
        log.info("Coach updated successfully with id: {}", id);
        return convertToDTO(updatedCoach);
    }

    /**
     * Delete coach by ID
     * @param id the coach ID
     * @throws ResourceNotFoundException if coach not found
     */
    public void deleteCoach(Long id) {
        log.info("Deleting coach with id: {}", id);

        if (!coachRepository.existsById(id)) {
            throw new ResourceNotFoundException("Coach not found with id: " + id);
        }

        coachRepository.deleteById(id);
        log.info("Coach deleted successfully with id: {}", id);
    }

    /**
     * Search coaches by name
     * @param name the name to search for
     * @return List of matching coaches
     */
    @Transactional(readOnly = true)
    public List<CoachDTO> searchCoachesByName(String name) {
        log.info("Searching coaches with name: {}", name);
        return coachRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get coaches by specialization
     * @param specialization the specialization to filter by
     * @return List of coaches with given specialization
     */
    @Transactional(readOnly = true)
    public List<CoachDTO> getCoachesBySpecialization(String specialization) {
        log.info("Fetching coaches with specialization: {}", specialization);
        return coachRepository.findBySpecialization(specialization).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get total count of coaches
     * @return total number of coaches
     */
    @Transactional(readOnly = true)
    public long getTotalCoachCount() {
        log.info("Fetching total coach count");
        return coachRepository.count();
    }

    /**
     * Convert Entity to DTO
     */
    private CoachDTO convertToDTO(Coach coach) {
        return new CoachDTO(
                coach.getCoachId(),
                coach.getName(),
                coach.getSpecialization(),
                coach.getPhone(),
                coach.getEmail(),
                coach.getCreatedAt(),
                coach.getUpdatedAt()
        );
    }

    /**
     * Convert DTO to Entity
     */
    private Coach convertToEntity(CoachDTO dto) {
        return new Coach(
                dto.getName(),
                dto.getSpecialization(),
                dto.getPhone(),
                dto.getEmail()
        );
    }
}
