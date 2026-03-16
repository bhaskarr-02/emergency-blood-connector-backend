package com.bloodconnector.controller;

import com.bloodconnector.entity.BloodRequest;
import com.bloodconnector.entity.Donor;
import com.bloodconnector.entity.User;
import com.bloodconnector.repository.BloodRequestRepository;
import com.bloodconnector.repository.DonorRepository;
import com.bloodconnector.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    /**
     * Dashboard summary stats
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {

        Map<String, Object> stats = new HashMap<>();

        stats.put("totalUsers", userRepository.count());
        stats.put("totalDonors", donorRepository.count());
        stats.put("availableDonors",
                donorRepository.countByAvailability(Donor.AvailabilityStatus.AVAILABLE));

        stats.put("totalRequests", bloodRequestRepository.count());
        stats.put("openRequests",
                bloodRequestRepository.countByStatus(BloodRequest.RequestStatus.OPEN));

        stats.put("fulfilledRequests",
                bloodRequestRepository.countByStatus(BloodRequest.RequestStatus.FULFILLED));

        stats.put("criticalRequests",
                bloodRequestRepository.findByUrgencyAndStatus(
                        BloodRequest.UrgencyLevel.CRITICAL,
                        BloodRequest.RequestStatus.OPEN
                ).size());

        return ResponseEntity.ok(stats);
    }

    /**
     * Get all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {

        List<Map<String, Object>> users = userRepository.findAll()
                .stream()
                .map(user -> {

                    Map<String, Object> u = new HashMap<>();

                    u.put("id", user.getId());
                    u.put("fullName", user.getFullName());
                    u.put("email", user.getEmail());
                    u.put("phone", user.getPhone());
                    u.put("role", user.getRole());
                    u.put("createdAt", user.getCreatedAt());

                    return u;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    /**
     * Update user role
     */
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id,
                                            @RequestBody Map<String, String> body) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {

            User.Role role = User.Role.valueOf(body.get("role"));

            user.setRole(role);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "Role updated successfully"));

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid role"));
        }
    }

    /**
     * Delete user
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);

        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}