package com.champsoft.vrms.registration.infrastructure.acl;

import com.champsoft.vrms.registration.application.exception.CrossContextValidationException;
import com.champsoft.vrms.registration.application.port.out.VehicleEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class VehicleEligibilityRestAdapter implements VehicleEligibilityPort {

    private final RestTemplate restTemplate;
    private final String carsBaseUrl;

    public VehicleEligibilityRestAdapter(
            RestTemplate restTemplate,
            @Value("${services.cars.base-url}") String carsBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.carsBaseUrl = carsBaseUrl;
    }

    @Override
    public boolean isEligible(String vehicleId) {
        String url = carsBaseUrl + "/api/cars/" + vehicleId + "/eligibility";
        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CrossContextValidationException("Vehicle not found: " + vehicleId);
        } catch (RestClientException ex) {
            throw new CrossContextValidationException("Unable to validate vehicle eligibility: " + vehicleId);
        }
    }
}
