package es.globalhands.dto;

import es.globalhands.model.Organization;

public record OrganizationResponse(
        Long id,
        String name,
        String description,
        String email,
        String phone,
        String city,
        String country
) {
    public static OrganizationResponse from(Organization organization) {
        return new OrganizationResponse(
                organization.getId(), organization.getName(), organization.getDescription(),
                organization.getEmail(), organization.getPhone(), organization.getCity(),
                organization.getCountry()
        );
    }
}
