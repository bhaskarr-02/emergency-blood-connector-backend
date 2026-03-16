package com.bloodconnector.repository;

import com.bloodconnector.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByBloodGroupAndAvailability(
            Donor.BloodGroup bloodGroup,
            Donor.AvailabilityStatus availability
    );

    List<Donor> findByCityIgnoreCaseAndBloodGroupAndAvailability(
            String city,
            Donor.BloodGroup bloodGroup,
            Donor.AvailabilityStatus availability
    );

    List<Donor> findByCityIgnoreCase(String city);

    List<Donor> findByBloodGroup(Donor.BloodGroup bloodGroup);

    Optional<Donor> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    long countByAvailability(Donor.AvailabilityStatus availability);

    @Query("SELECT d FROM Donor d WHERE " +
           "(:bloodGroup IS NULL OR d.bloodGroup = :bloodGroup) AND " +
           "(:city IS NULL OR LOWER(d.city) = LOWER(:city)) AND " +
           "(:state IS NULL OR LOWER(d.state) = LOWER(:state)) AND " +
           "d.availability = :availability")
    List<Donor> searchAvailableDonors(
            @Param("bloodGroup") Donor.BloodGroup bloodGroup,
            @Param("city") String city,
            @Param("state") String state,
            @Param("availability") Donor.AvailabilityStatus availability
    );
}