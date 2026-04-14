package com.champsoft.vrms.agents.infrastructure.acl;

import com.champsoft.vrms.agents.application.port.out.RegistrationUsagePort;
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
    public boolean hasRegistrations(String agentId) {
        String url = registrationBaseUrl + "/api/registrations/agents/" + agentId + "/exists";
        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (RestClientException ex) {
            throw new IllegalStateException("Unable to validate agent registrations for " + agentId, ex);
        }
    }
}
