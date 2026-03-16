package com.bloodconnector.dto;

import com.bloodconnector.entity.BloodRequest;
import com.bloodconnector.entity.Donor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RequestDTO {

    // ---------------- Create Request ----------------
    public static class CreateRequest {

        @NotBlank(message = "Patient name is required")
        private String patientName;

        @NotNull(message = "Blood group is required")
        private Donor.BloodGroup bloodGroup;

        @NotNull(message = "Units required is required")
        @Min(value = 1, message = "At least 1 unit is required")
        private Integer unitsRequired;

        @NotNull(message = "Urgency level is required")
        private BloodRequest.UrgencyLevel urgency;

        @NotBlank(message = "Hospital name is required")
        private String hospital;

        @NotBlank(message = "City is required")
        private String city;

        @NotBlank(message = "State is required")
        private String state;

        @NotBlank(message = "Contact phone is required")
        private String contactPhone;

        private String additionalInfo;

        public CreateRequest() {}

        public CreateRequest(String patientName, Donor.BloodGroup bloodGroup, Integer unitsRequired,
                             BloodRequest.UrgencyLevel urgency, String hospital,
                             String city, String state, String contactPhone,
                             String additionalInfo) {
            this.patientName = patientName;
            this.bloodGroup = bloodGroup;
            this.unitsRequired = unitsRequired;
            this.urgency = urgency;
            this.hospital = hospital;
            this.city = city;
            this.state = state;
            this.contactPhone = contactPhone;
            this.additionalInfo = additionalInfo;
        }

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }

        public Donor.BloodGroup getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(Donor.BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

        public Integer getUnitsRequired() { return unitsRequired; }
        public void setUnitsRequired(Integer unitsRequired) { this.unitsRequired = unitsRequired; }

        public BloodRequest.UrgencyLevel getUrgency() { return urgency; }
        public void setUrgency(BloodRequest.UrgencyLevel urgency) { this.urgency = urgency; }

        public String getHospital() { return hospital; }
        public void setHospital(String hospital) { this.hospital = hospital; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getContactPhone() { return contactPhone; }
        public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

        public String getAdditionalInfo() { return additionalInfo; }
        public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }
    }

    // ---------------- Update Status ----------------
    public static class UpdateStatus {

        @NotNull(message = "Status is required")
        private BloodRequest.RequestStatus status;

        public UpdateStatus() {}

        public UpdateStatus(BloodRequest.RequestStatus status) {
            this.status = status;
        }

        public BloodRequest.RequestStatus getStatus() { return status; }
        public void setStatus(BloodRequest.RequestStatus status) { this.status = status; }
    }

    // ---------------- Response ----------------
    public static class Response {

        private Long id;
        private String patientName;
        private String bloodGroup;
        private Integer unitsRequired;
        private String urgency;
        private String hospital;
        private String city;
        private String state;
        private String contactPhone;
        private String additionalInfo;
        private String status;
        private String requesterName;
        private LocalDateTime createdAt;

        public Response() {}

        public Response(Long id, String patientName, String bloodGroup,
                        Integer unitsRequired, String urgency,
                        String hospital, String city, String state,
                        String contactPhone, String additionalInfo,
                        String status, String requesterName,
                        LocalDateTime createdAt) {
            this.id = id;
            this.patientName = patientName;
            this.bloodGroup = bloodGroup;
            this.unitsRequired = unitsRequired;
            this.urgency = urgency;
            this.hospital = hospital;
            this.city = city;
            this.state = state;
            this.contactPhone = contactPhone;
            this.additionalInfo = additionalInfo;
            this.status = status;
            this.requesterName = requesterName;
            this.createdAt = createdAt;
        }

        public static Response from(BloodRequest req) {
            return new Response(
                    req.getId(),
                    req.getPatientName(),
                    req.getBloodGroup().getLabel(),
                    req.getUnitsRequired(),
                    req.getUrgency().name(),
                    req.getHospital(),
                    req.getCity(),
                    req.getState(),
                    req.getContactPhone(),
                    req.getAdditionalInfo(),
                    req.getStatus().name(),
                    req.getRequester() != null ? req.getRequester().getFullName() : null,
                    req.getCreatedAt()
            );
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }

        public String getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

        public Integer getUnitsRequired() { return unitsRequired; }
        public void setUnitsRequired(Integer unitsRequired) { this.unitsRequired = unitsRequired; }

        public String getUrgency() { return urgency; }
        public void setUrgency(String urgency) { this.urgency = urgency; }

        public String getHospital() { return hospital; }
        public void setHospital(String hospital) { this.hospital = hospital; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getContactPhone() { return contactPhone; }
        public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

        public String getAdditionalInfo() { return additionalInfo; }
        public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getRequesterName() { return requesterName; }
        public void setRequesterName(String requesterName) { this.requesterName = requesterName; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}