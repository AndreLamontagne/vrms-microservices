package com.champsoft.vrms.registration.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataRegistrationRepository extends JpaRepository<RegistrationJpaEntity, String> {
    Optional<RegistrationJpaEntity> findByPlate(String plate);
    boolean existsByPlate(String plate);
    boolean existsByVehicleId(String vehicleId);
    boolean existsByOwnerId(String ownerId);
    boolean existsByAgentId(String agentId);
}

