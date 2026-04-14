package com.champsoft.vrms.registration.infrastructure.acl;

import com.champsoft.vrms.registration.application.exception.CrossContextValidationException;
import com.champsoft.vrms.registration.application.port.out.AgentEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AgentEligibilityRestAdapter implements AgentEligibilityPort {

    private final RestTemplate restTemplate;
    private final String agentsBaseUrl;

    public AgentEligibilityRestAdapter(
            RestTemplate restTemplate,
            @Value("${services.agents.base-url}") String agentsBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.agentsBaseUrl = agentsBaseUrl;
    }

    @Override
    public boolean isEligible(String agentId) {
        String url = agentsBaseUrl + "/api/agents/" + agentId + "/eligibility";
        try {
            Boolean response = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(response);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CrossContextValidationException("Agent not found: " + agentId);
        } catch (RestClientException ex) {
            throw new CrossContextValidationException("Unable to validate agent eligibility: " + agentId);
        }
    }
}
