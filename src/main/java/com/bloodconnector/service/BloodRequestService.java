package com.bloodconnector.service;

import com.bloodconnector.dto.RequestDTO;
import com.bloodconnector.entity.BloodRequest;
import com.bloodconnector.entity.Donor;
import com.bloodconnector.entity.User;
import com.bloodconnector.repository.BloodRequestRepository;
import com.bloodconnector.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BloodRequestService {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public RequestDTO.Response createRequest(RequestDTO.CreateRequest request, String userEmail) {

        User requester = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BloodRequest bloodRequest = new BloodRequest();

        bloodRequest.setRequester(requester);
        bloodRequest.setPatientName(request.getPatientName());
        bloodRequest.setBloodGroup(request.getBloodGroup());
        bloodRequest.setUnitsRequired(request.getUnitsRequired());
        bloodRequest.setUrgency(request.getUrgency());
        bloodRequest.setHospital(request.getHospital());
        bloodRequest.setCity(request.getCity());
        bloodRequest.setState(request.getState());
        bloodRequest.setContactPhone(request.getContactPhone());
        bloodRequest.setAdditionalInfo(request.getAdditionalInfo());
        bloodRequest.setStatus(BloodRequest.RequestStatus.OPEN);

        return RequestDTO.Response.from(bloodRequestRepository.save(bloodRequest));
    }

    public RequestDTO.Response getRequestById(Long id) {

        BloodRequest request = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood request not found"));

        return RequestDTO.Response.from(request);
    }

    public List<RequestDTO.Response> getOpenRequests() {

        return bloodRequestRepository.findByStatus(BloodRequest.RequestStatus.OPEN)
                .stream()
                .map(RequestDTO.Response::from)
                .collect(Collectors.toList());
    }

    public List<RequestDTO.Response> getMyRequests(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bloodRequestRepository.findByRequesterId(user.getId())
                .stream()
                .map(RequestDTO.Response::from)
                .collect(Collectors.toList());
    }

    public List<RequestDTO.Response> searchRequests(Donor.BloodGroup bloodGroup,
                                                    String city,
                                                    BloodRequest.UrgencyLevel urgency) {

        return bloodRequestRepository.searchOpenRequests(
                bloodGroup,
                city,
                urgency,
                BloodRequest.RequestStatus.OPEN
        )
                .stream()
                .map(RequestDTO.Response::from)
                .collect(Collectors.toList());
    }

    public RequestDTO.Response updateStatus(Long id,
                                            RequestDTO.UpdateStatus updateStatus,
                                            String userEmail) {

        BloodRequest request = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood request not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isOwner = request.getRequester() != null &&
                request.getRequester().getEmail().equals(userEmail);

        boolean isAdmin = user.getRole() == User.Role.ROLE_ADMIN;

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Unauthorized: Cannot update this request");
        }

        request.setStatus(updateStatus.getStatus());

        if (updateStatus.getStatus() == BloodRequest.RequestStatus.FULFILLED) {
            request.setFulfilledAt(LocalDateTime.now());
        }

        return RequestDTO.Response.from(bloodRequestRepository.save(request));
    }

    public List<RequestDTO.Response> getAllRequests() {

        return bloodRequestRepository.findAll()
                .stream()
                .map(RequestDTO.Response::from)
                .collect(Collectors.toList());
    }

    public void deleteRequest(Long id) {

        if (!bloodRequestRepository.existsById(id)) {
            throw new RuntimeException("Blood request not found");
        }

        bloodRequestRepository.deleteById(id);
    }
}