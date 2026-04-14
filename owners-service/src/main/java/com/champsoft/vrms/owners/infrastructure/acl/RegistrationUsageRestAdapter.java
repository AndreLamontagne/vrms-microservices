package com.champsoft.vrms.owners.infrastructure.acl;

import com.champsoft.vrms.owners.application.port.out.RegistrationUsagePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RegistrationUsageRestAdapter implements RegistrationUsagePort {

    private final RestTemplate restTemplate;
    private final String registrationBaseUrl;

    public RegistrationUsageRestAdapter(
            RestTemplate restTemplate,
            @Value("${services.registration.base-url}") String registrationBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.registrationBaseUrl = registrationBaseUrl;
    }

    @Override
    public boolean hasRegistrations(String ownerId) {
        String url = registrationBaseUrl + "/api/registrations/owners/" + ownerId + "/exists";
        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (RestClientException ex) {
            throw new IllegalStateException("Unable to validate owner registrations for " + ownerId, ex);
        }
    }
}
