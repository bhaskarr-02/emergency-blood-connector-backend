package com.bloodconnector.repository;

import com.bloodconnector.entity.BloodRequest;
import com.bloodconnector.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    List<BloodRequest> findByStatus(BloodRequest.RequestStatus status);

    List<BloodRequest> findByBloodGroupAndStatus(Donor.BloodGroup bloodGroup, BloodRequest.RequestStatus status);

    List<BloodRequest> findByCityIgnoreCaseAndStatus(String city, BloodRequest.RequestStatus status);

    List<BloodRequest> findByRequesterId(Long requesterId);

    List<BloodRequest> findByUrgencyAndStatus(BloodRequest.UrgencyLevel urgency, BloodRequest.RequestStatus status);

    long countByStatus(BloodRequest.RequestStatus status);

    @Query("SELECT r FROM BloodRequest r WHERE " +
           "(:bloodGroup IS NULL OR r.bloodGroup = :bloodGroup) AND " +
           "(:city IS NULL OR LOWER(r.city) = LOWER(:city)) AND " +
           "(:urgency IS NULL OR r.urgency = :urgency) AND " +
           "r.status = :status ORDER BY r.urgency ASC, r.createdAt DESC")
    List<BloodRequest> searchOpenRequests(
            @Param("bloodGroup") Donor.BloodGroup bloodGroup,
            @Param("city") String city,
            @Param("urgency") BloodRequest.UrgencyLevel urgency,
            @Param("status") BloodRequest.RequestStatus status
    );
}