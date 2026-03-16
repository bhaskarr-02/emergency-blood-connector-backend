package com.bloodconnector.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "donors")
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    private String address;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus availability = AvailabilityStatus.AVAILABLE;

    private LocalDate lastDonationDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String medicalNotes;

    // Default Constructor
    public Donor() {}

    // Parameterized Constructor
    public Donor(Long id, User user, String fullName, String phone,
                 BloodGroup bloodGroup, String city, String state,
                 String address, Integer age, AvailabilityStatus availability,
                 LocalDate lastDonationDate, LocalDateTime registeredAt,
                 LocalDateTime updatedAt, String medicalNotes) {

        this.id = id;
        this.user = user;
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
        this.updatedAt = updatedAt;
        this.medicalNotes = medicalNotes;
    }

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public BloodGroup getBloodGroup() { return bloodGroup; }

    public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public AvailabilityStatus getAvailability() { return availability; }

    public void setAvailability(AvailabilityStatus availability) { this.availability = availability; }

    public LocalDate getLastDonationDate() { return lastDonationDate; }

    public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }

    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getMedicalNotes() { return medicalNotes; }

    public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }

    // Blood Group Enum
    public enum BloodGroup {
        A_POSITIVE("A+"), A_NEGATIVE("A-"),
        B_POSITIVE("B+"), B_NEGATIVE("B-"),
        AB_POSITIVE("AB+"), AB_NEGATIVE("AB-"),
        O_POSITIVE("O+"), O_NEGATIVE("O-");

        private final String label;

        BloodGroup(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    // Availability Enum
    public enum AvailabilityStatus {
        AVAILABLE,
        UNAVAILABLE,
        DONATED_RECENTLY
    }
}