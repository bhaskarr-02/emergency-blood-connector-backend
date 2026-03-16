package com.bloodconnector.dto;

import com.bloodconnector.entity.Donor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DonorDTO {

    // ---------------- Create Request ----------------
    public static class CreateRequest {

        @NotBlank(message = "Full name is required")
        private String fullName;

        @NotBlank(message = "Phone number is required")
        private String phone;

        @NotNull(message = "Blood group is required")
        private Donor.BloodGroup bloodGroup;

        @NotBlank(message = "City is required")
        private String city;

        @NotBlank(message = "State is required")
        private String state;

        private String address;

        @NotNull(message = "Age is required")
        private Integer age;

        private LocalDate lastDonationDate;

        private String medicalNotes;

        public CreateRequest() {}

        public CreateRequest(String fullName, String phone, Donor.BloodGroup bloodGroup,
                             String city, String state, String address,
                             Integer age, LocalDate lastDonationDate, String medicalNotes) {
            this.fullName = fullName;
            this.phone = phone;
            this.bloodGroup = bloodGroup;
            this.city = city;
            this.state = state;
            this.address = address;
            this.age = age;
            this.lastDonationDate = lastDonationDate;
            this.medicalNotes = medicalNotes;
        }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public Donor.BloodGroup getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(Donor.BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }

        public LocalDate getLastDonationDate() { return lastDonationDate; }
        public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }

        public String getMedicalNotes() { return medicalNotes; }
        public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }
    }


    // ---------------- Update Request ----------------
    public static class UpdateRequest {

        private String phone;
        private String city;
        private String state;
        private String address;
        private Donor.AvailabilityStatus availability;
        private LocalDate lastDonationDate;
        private String medicalNotes;

        public UpdateRequest() {}

        public UpdateRequest(String phone, String city, String state, String address,
                             Donor.AvailabilityStatus availability,
                             LocalDate lastDonationDate, String medicalNotes) {
            this.phone = phone;
            this.city = city;
            this.state = state;
            this.address = address;
            this.availability = availability;
            this.lastDonationDate = lastDonationDate;
            this.medicalNotes = medicalNotes;
        }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public Donor.AvailabilityStatus getAvailability() { return availability; }
        public void setAvailability(Donor.AvailabilityStatus availability) { this.availability = availability; }

        public LocalDate getLastDonationDate() { return lastDonationDate; }
        public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }

        public String getMedicalNotes() { return medicalNotes; }
        public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }
    }


    // ---------------- Response ----------------
    public static class Response {

        private Long id;
        private String fullName;
        private String phone;
        private String bloodGroup;
        private String city;
        private String state;
        private String address;
        private Integer age;
        private String availability;
        private LocalDate lastDonationDate;
        private LocalDateTime registeredAt;

        public Response() {}

        public Response(Long id, String fullName, String phone, String bloodGroup,
                        String city, String state, String address, Integer age,
                        String availability, LocalDate lastDonationDate,
                        LocalDateTime registeredAt) {
            this.id = id;
            this.fullName = fullName;
            this.phone = phone;
            this.bloodGroup = bloodGroup;
            this.city = city;
            this.state = state;
            this.address = address;
            this.age = age;
            this.availability = availability;
            this.lastDonationDate = lastDonationDate;
            this.registeredAt = registeredAt;
        }

        public static Response from(Donor donor) {
            return new Response(
                    donor.getId(),
                    donor.getFullName(),
                    donor.getPhone(),
                    donor.getBloodGroup().getLabel(),
                    donor.getCity(),
                    donor.getState(),
                    donor.getAddress(),
                    donor.getAge(),
                    donor.getAvailability().name(),
                    donor.getLastDonationDate(),
                    donor.getRegisteredAt()
            );
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }

        public String getAvailability() { return availability; }
        public void setAvailability(String availability) { this.availability = availability; }

        public LocalDate getLastDonationDate() { return lastDonationDate; }
        public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }

        public LocalDateTime getRegisteredAt() { return registeredAt; }
        public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    }
}