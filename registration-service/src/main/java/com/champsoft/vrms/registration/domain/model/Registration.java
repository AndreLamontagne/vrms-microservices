package com.champsoft.vrms.registration.domain.model;

import com.champsoft.vrms.registration.domain.exception.RegistrationNotActiveException;

import java.time.LocalDate;

public class Registration {
    private final RegistrationId id;
    private final VehicleRef vehicleId;
    private final OwnerRef ownerId;
    private final AgentRef agentId;

    private PlateNumber plate;
    private ExpiryDate expiry;
    private RegistrationStatus status;

    public Registration(
            RegistrationId id,
            VehicleRef vehicleId,
            OwnerRef ownerId,
            AgentRef agentId,
            PlateNumber plate,
            ExpiryDate expiry
    ) {
        this(id, vehicleId, ownerId, agentId, plate, expiry, RegistrationStatus.ACTIVE);
    }

    public Registration(
            RegistrationId id,
            VehicleRef vehicleId,
            OwnerRef ownerId,
            AgentRef agentId,
            PlateNumber plate,
            ExpiryDate expiry,
            RegistrationStatus status
    ) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.agentId = agentId;
        this.plate = plate;
        this.expiry = expiry;
        this.status = status;
    }

    public static Registration createNew(
            RegistrationId id,
            VehicleRef vehicleId,
            OwnerRef ownerId,
            AgentRef agentId,
            PlateNumber plate,
            LocalDate expiry
    ) {
        return new Registration(
                id,
                vehicleId,
                ownerId,
                agentId,
                plate,
                ExpiryDate.forNewRegistration(expiry),
                RegistrationStatus.ACTIVE
        );
    }

    public RegistrationId id() { return id; }
    public VehicleRef vehicleId() { return vehicleId; }
    public OwnerRef ownerId() { return ownerId; }
    public AgentRef agentId() { return agentId; }
    public PlateNumber plate() { return plate; }
    public ExpiryDate expiry() { return expiry; }
    public RegistrationStatus status() { return status; }

    public String vehicleIdValue() { return vehicleId.value(); }
    public String ownerIdValue() { return ownerId.value(); }
    public String agentIdValue() { return agentId.value(); }
    public String plateValue() { return plate.value(); }
    public LocalDate expiryValue() { return expiry.value(); }

    public void renew(LocalDate newExpiry) {
        if (status != RegistrationStatus.ACTIVE) {
            throw new RegistrationNotActiveException("Registration is not ACTIVE");
        }
        this.expiry = ExpiryDate.forNewRegistration(newExpiry);
    }

    public void cancel() {
        this.status = RegistrationStatus.CANCELLED;
    }
}
