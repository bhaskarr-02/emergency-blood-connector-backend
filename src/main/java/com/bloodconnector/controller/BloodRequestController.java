package com.bloodconnector.controller;

import com.bloodconnector.dto.RequestDTO;
import com.bloodconnector.entity.BloodRequest;
import com.bloodconnector.entity.Donor;
import com.bloodconnector.service.BloodRequestService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    /**
     * Public: Get all open blood requests
     */
    @GetMapping("/open")
    public ResponseEntity<List<RequestDTO.Response>> getOpenRequests() {

        return ResponseEntity.ok(
                bloodRequestService.getOpenRequests()
        );
    }

    /**
     * Public: Search open blood requests
     */
    @GetMapping("/search")
    public ResponseEntity<List<RequestDTO.Response>> searchRequests(
            @RequestParam(required = false) Donor.BloodGroup bloodGroup,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BloodRequest.UrgencyLevel urgency) {

        return ResponseEntity.ok(
                bloodRequestService.searchRequests(bloodGroup, city, urgency)
        );
    }

    /**
     * Authenticated: Create a blood request
     */
    @PostMapping
    public ResponseEntity<?> createRequest(
            @Valid @RequestBody RequestDTO.CreateRequest request,
            Authentication authentication) {

        try {

            RequestDTO.Response response =
                    bloodRequestService.createRequest(request, authentication.getName());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get blood request by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRequestById(@PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    bloodRequestService.getRequestById(id)
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get my blood requests
     */
    @GetMapping("/my")
    public ResponseEntity<List<RequestDTO.Response>> getMyRequests(Authentication authentication) {

        return ResponseEntity.ok(
                bloodRequestService.getMyRequests(authentication.getName())
        );
    }

    /**
     * Update request status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody RequestDTO.UpdateStatus updateStatus,
            Authentication authentication) {

        try {

            RequestDTO.Response response =
                    bloodRequestService.updateStatus(id, updateStatus, authentication.getName());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Admin: Get all requests
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RequestDTO.Response>> getAllRequests() {

        return ResponseEntity.ok(
                bloodRequestService.getAllRequests()
        );
    }

    /**
     * Admin: Delete request
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) {

        try {

            bloodRequestService.deleteRequest(id);

            return ResponseEntity.ok(
                    new MessageResponse("Request deleted successfully")
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
     * Success message response
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