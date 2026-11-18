package es.globalhands.service;

import es.globalhands.dto.ApplicationRequest;
import es.globalhands.dto.ApplicationResponse;
import es.globalhands.model.User;
import es.globalhands.model.VolunteerApplication;
import es.globalhands.model.VolunteerOpportunity;
import es.globalhands.repository.UserRepository;
import es.globalhands.repository.VolunteerApplicationRepository;
import es.globalhands.repository.VolunteerOpportunityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ApplicationService {

    private final UserRepository userRepository;
    private final VolunteerOpportunityRepository opportunityRepository;
    private final VolunteerApplicationRepository applicationRepository;

    public ApplicationService(UserRepository userRepository,
                              VolunteerOpportunityRepository opportunityRepository,
                              VolunteerApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.opportunityRepository = opportunityRepository;
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public ApplicationResponse apply(Long userId, Long opportunityId, ApplicationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        VolunteerOpportunity opportunity = opportunityRepository.findById(opportunityId)
                .filter(item -> "publicado".equals(item.getStatus()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opportunity not found"));

        if (applicationRepository.existsByUserAndOpportunity(user, opportunity)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already applied to this opportunity");
        }

        String message = request == null || request.message() == null || request.message().isBlank()
                ? null
                : request.message().trim();
        VolunteerApplication application = applicationRepository.save(
                new VolunteerApplication(user, opportunity, message)
        );
        return ApplicationResponse.from(application);
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getForUser(Long userId) {
        return applicationRepository.findByUser_IdOrderBySubmittedAtDesc(userId)
                .stream().map(ApplicationResponse::from).toList();
    }
}
