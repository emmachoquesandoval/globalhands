package es.globalhands.controller;

import es.globalhands.dto.ApplicationRequest;
import es.globalhands.dto.ApplicationResponse;
import es.globalhands.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VolunteerApplicationController {

    private final ApplicationService applicationService;

    public VolunteerApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/opportunities/{opportunityId}/applications")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse apply(
            @PathVariable Long opportunityId,
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody(required = false) ApplicationRequest request
    ) {
        return applicationService.apply(userId(jwt), opportunityId, request);
    }

    @GetMapping("/applications/me")
    public List<ApplicationResponse> getMyApplications(@AuthenticationPrincipal Jwt jwt) {
        return applicationService.getForUser(userId(jwt));
    }

    private Long userId(Jwt jwt) {
        return ((Number) jwt.getClaim("userId")).longValue();
    }
}
