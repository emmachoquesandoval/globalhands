package es.globalhands.service;

import es.globalhands.dto.OrganizationResponse;
import es.globalhands.repository.OrganizationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Transactional(readOnly = true)
    public List<OrganizationResponse> getAll() {
        return organizationRepository.findAll().stream().map(OrganizationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public OrganizationResponse getById(Long id) {
        return organizationRepository.findById(id)
                .map(OrganizationResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organization not found"));
    }
}
