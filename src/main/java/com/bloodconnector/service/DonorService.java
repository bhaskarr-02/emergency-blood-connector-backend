package com.bloodconnector.service;

import com.bloodconnector.dto.DonorDTO;
import com.bloodconnector.entity.Donor;
import com.bloodconnector.entity.User;
import com.bloodconnector.repository.DonorRepository;
import com.bloodconnector.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    public DonorDTO.Response registerDonor(DonorDTO.CreateRequest request, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (donorRepository.existsByUserId(user.getId())) {
            throw new RuntimeException("You are already registered as a donor");
        }

        Donor donor = new Donor();

        donor.setUser(user);
        donor.setFullName(request.getFullName());
        donor.setPhone(request.getPhone());
        donor.setBloodGroup(request.getBloodGroup());
        donor.setCity(request.getCity());
        donor.setState(request.getState());
        donor.setAddress(request.getAddress());
        donor.setAge(request.getAge());
        donor.setLastDonationDate(request.getLastDonationDate());
        donor.setMedicalNotes(request.getMedicalNotes());
        donor.setAvailability(Donor.AvailabilityStatus.AVAILABLE);

        donor = donorRepository.save(donor);

        // Update user role to donor
        user.setRole(User.Role.ROLE_DONOR);
        userRepository.save(user);

        return DonorDTO.Response.from(donor);
    }

    public DonorDTO.Response getDonorById(Long id) {

        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        return DonorDTO.Response.from(donor);
    }

    public DonorDTO.Response getDonorByUser(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donor donor = donorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Donor profile not found"));

        return DonorDTO.Response.from(donor);
    }

    public DonorDTO.Response updateDonor(Long id, DonorDTO.UpdateRequest request, String userEmail) {

        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        verifyOwnership(donor, userEmail);

        if (request.getPhone() != null) donor.setPhone(request.getPhone());
        if (request.getCity() != null) donor.setCity(request.getCity());
        if (request.getState() != null) donor.setState(request.getState());
        if (request.getAddress() != null) donor.setAddress(request.getAddress());
        if (request.getAvailability() != null) donor.setAvailability(request.getAvailability());
        if (request.getLastDonationDate() != null) donor.setLastDonationDate(request.getLastDonationDate());
        if (request.getMedicalNotes() != null) donor.setMedicalNotes(request.getMedicalNotes());

        return DonorDTO.Response.from(donorRepository.save(donor));
    }

    public List<DonorDTO.Response> searchDonors(Donor.BloodGroup bloodGroup,
                                                String city,
                                                String state) {

        return donorRepository.searchAvailableDonors(
                bloodGroup,
                city,
                state,
                Donor.AvailabilityStatus.AVAILABLE
        )
                .stream()
                .map(DonorDTO.Response::from)
                .collect(Collectors.toList());
    }

    public List<DonorDTO.Response> getAllDonors() {

        return donorRepository.findAll()
                .stream()
                .map(DonorDTO.Response::from)
                .collect(Collectors.toList());
    }

    public void deleteDonor(Long id) {

        if (!donorRepository.existsById(id)) {
            throw new RuntimeException("Donor not found");
        }

        donorRepository.deleteById(id);
    }

    private void verifyOwnership(Donor donor, String userEmail) {

        if (donor.getUser() == null || !donor.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized: You can only modify your own donor profile");
        }
    }
}