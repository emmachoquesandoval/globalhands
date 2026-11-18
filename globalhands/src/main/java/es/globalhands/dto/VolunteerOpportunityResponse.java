package es.globalhands.dto;

import es.globalhands.model.VolunteerOpportunity;

import java.time.LocalDate;
import java.util.List;

public record VolunteerOpportunityResponse(
        Long id,
        String title,
        String description,
        String modality,
        String city,
        String country,
        LocalDate startDate,
        LocalDate endDate,
        Integer places,
        String status,
        Long organizationId,
        String organizationName,
        List<CategoryResponse> categories
) {
    public static VolunteerOpportunityResponse from(VolunteerOpportunity opportunity) {
        return new VolunteerOpportunityResponse(
                opportunity.getId(), opportunity.getTitle(), opportunity.getDescription(),
                opportunity.getModality(), opportunity.getCity(), opportunity.getCountry(),
                opportunity.getStartDate(), opportunity.getEndDate(), opportunity.getPlaces(),
                opportunity.getStatus(), opportunity.getOrganization().getId(),
                opportunity.getOrganization().getName(),
                opportunity.getCategories().stream().map(CategoryResponse::from).toList()
        );
    }
}
