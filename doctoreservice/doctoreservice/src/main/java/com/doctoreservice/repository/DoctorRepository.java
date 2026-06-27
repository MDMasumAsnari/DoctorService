package com.doctoreservice.repository;

import com.doctoreservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Search by specialization + city name (case-insensitive)
    @Query("SELECT d FROM Doctor d " +
            "WHERE LOWER(d.specialization) = LOWER(:specialization) " +
            "AND LOWER(d.area.name) = LOWER(:areaName)")
    List<Doctor> findBySpecializationAndArea(@Param("specialization") String specialization,
                                             @Param("areaName") String areaName);
}
