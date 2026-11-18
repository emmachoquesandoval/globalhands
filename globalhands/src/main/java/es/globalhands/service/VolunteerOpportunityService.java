package es.globalhands.service;

import es.globalhands.dto.VolunteerOpportunityResponse;
import es.globalhands.model.VolunteerOpportunity;
import es.globalhands.repository.VolunteerOpportunityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VolunteerOpportunityService {

    private static final int MAX_PAGE_SIZE = 50;

    private final VolunteerOpportunityRepository opportunityRepository;

    public VolunteerOpportunityService(VolunteerOpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    @Transactional(readOnly = true)
    public Page<VolunteerOpportunityResponse> search(
            String search,
            String country,
            String city,
            String modality,
            Long categoryId,
            Long organizationId,
            int page,
            int size
    ) {
        if (page < 0 || size < 1 || size > MAX_PAGE_SIZE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page must be zero or greater and size must be between 1 and 50"
            );
        }
        if (modality != null && !modality.isBlank()
                && !modality.equals("presencial")
                && !modality.equals("online")
                && !modality.equals("hibrida")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported modality");
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return opportunityRepository.search(
                        clean(search), clean(country), clean(city), clean(modality),
                        categoryId, organizationId, "publicado", pageable
                )
                .map(VolunteerOpportunityResponse::from);
    }

    @Transactional(readOnly = true)
    public VolunteerOpportunityResponse getPublishedById(Long id) {
        VolunteerOpportunity opportunity = opportunityRepository.findById(id)
                .filter(item -> "publicado".equals(item.getStatus()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity not found"));
        return VolunteerOpportunityResponse.from(opportunity);
    }

    private String clean(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
