package es.globalhands.controller;

import es.globalhands.dto.VolunteerOpportunityResponse;
import es.globalhands.service.VolunteerOpportunityService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/opportunities")
public class VolunteerOpportunityController {

    private final VolunteerOpportunityService opportunityService;

    public VolunteerOpportunityController(VolunteerOpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @GetMapping
    public Page<VolunteerOpportunityResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String modality,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long organizationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return opportunityService.search(q, country, city, modality, categoryId, organizationId, page, size);
    }

    @GetMapping("/{id}")
    public VolunteerOpportunityResponse getById(@PathVariable Long id) {
        return opportunityService.getPublishedById(id);
    }
}
