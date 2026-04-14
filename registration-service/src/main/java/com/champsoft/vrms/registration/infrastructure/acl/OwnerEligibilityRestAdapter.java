package com.champsoft.vrms.registration.infrastructure.acl;

import com.champsoft.vrms.registration.application.exception.CrossContextValidationException;
import com.champsoft.vrms.registration.application.port.out.OwnerEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class OwnerEligibilityRestAdapter implements OwnerEligibilityPort {

    private final RestTemplate restTemplate;
    private final String ownersBaseUrl;

    public OwnerEligibilityRestAdapter(
            RestTemplate restTemplate,
            @Value("${services.owners.base-url}") String ownersBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.ownersBaseUrl = ownersBaseUrl;
    }

    @Override
    public boolean isEligible(String ownerId) {
        String url = ownersBaseUrl + "/api/owners/" + ownerId + "/eligibility";
        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CrossContextValidationException("Owner not found: " + ownerId);
        } catch (RestClientException ex) {
            throw new CrossContextValidationException("Unable to validate owner eligibility: " + ownerId);
        }
    }
}
