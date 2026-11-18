package es.globalhands.controller;

import es.globalhands.dto.OrganizationResponse;
import es.globalhands.service.OrganizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public List<OrganizationResponse> getOrganizations() {
        return organizationService.getAll();
    }

    @GetMapping("/{id}")
    public OrganizationResponse getOrganization(@PathVariable Long id) {
        return organizationService.getById(id);
    }
}
