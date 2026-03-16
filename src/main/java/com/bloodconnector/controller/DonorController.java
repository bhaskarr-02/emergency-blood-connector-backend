package com.bloodconnector.controller;

import com.bloodconnector.dto.DonorDTO;
import com.bloodconnector.entity.Donor;
import com.bloodconnector.service.DonorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "*")
public class DonorController {

    @Autowired
    private DonorService donorService;

    /**
     * Public: Search available donors
     */
    @GetMapping("/search")
    public ResponseEntity<List<DonorDTO.Response>> searchDonors(
            @RequestParam(required = false) Donor.BloodGroup bloodGroup,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {

        return ResponseEntity.ok(
                donorService.searchDonors(bloodGroup, city, state)
        );
    }

    /**
     * Register as donor
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerDonor(
            @Valid @RequestBody DonorDTO.CreateRequest request,
            Authentication authentication) {

        try {

            DonorDTO.Response response =
                    donorService.registerDonor(request, authentication.getName());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get my donor profile
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMyDonorProfile(Authentication authentication) {

        try {

            DonorDTO.Response response =
                    donorService.getDonorByUser(authentication.getName());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get donor by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDonorById(@PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    donorService.getDonorById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update donor profile
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDonor(
            @PathVariable Long id,
            @RequestBody DonorDTO.UpdateRequest request,
            Authentication authentication) {

        try {

            DonorDTO.Response response =
                    donorService.updateDonor(id, request, authentication.getName());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Admin: Get all donors
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DonorDTO.Response>> getAllDonors() {

        return ResponseEntity.ok(
                donorService.getAllDonors()
        );
    }

    /**
     * Admin: Delete donor
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDonor(@PathVariable Long id) {

        try {

            donorService.deleteDonor(id);

            return ResponseEntity.ok(
                    new MessageResponse("Donor deleted successfully")
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Error response
     */
    public static class ErrorResponse {

        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Success message
     */
    public static class MessageResponse {

        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}