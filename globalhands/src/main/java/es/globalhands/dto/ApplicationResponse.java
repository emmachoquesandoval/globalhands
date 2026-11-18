package es.globalhands.dto;

import es.globalhands.model.VolunteerApplication;

import java.time.LocalDateTime;

public record ApplicationResponse(
        Long id,
        Long opportunityId,
        String opportunityTitle,
        String message,
        String status,
        LocalDateTime submittedAt
) {
    public static ApplicationResponse from(VolunteerApplication application) {
        return new ApplicationResponse(
                application.getId(), application.getOpportunity().getId(),
                application.getOpportunity().getTitle(), application.getMessage(),
                application.getStatus(), application.getSubmittedAt()
        );
    }
}
