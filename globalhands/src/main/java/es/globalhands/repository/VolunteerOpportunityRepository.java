package es.globalhands.repository;

import es.globalhands.model.VolunteerOpportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerOpportunityRepository extends JpaRepository<VolunteerOpportunity, Long> {

    @Query(
            value = """
                    SELECT DISTINCT opportunity
                    FROM VolunteerOpportunity opportunity
                    LEFT JOIN opportunity.categories category
                    WHERE (:search IS NULL OR
                           LOWER(opportunity.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
                           LOWER(opportunity.description) LIKE LOWER(CONCAT('%', :search, '%')))
                      AND (:country IS NULL OR LOWER(opportunity.country) = LOWER(:country))
                      AND (:city IS NULL OR LOWER(opportunity.city) = LOWER(:city))
                      AND (:modality IS NULL OR opportunity.modality = :modality)
                      AND (:categoryId IS NULL OR category.id = :categoryId)
                      AND (:organizationId IS NULL OR opportunity.organization.id = :organizationId)
                      AND (:status IS NULL OR opportunity.status = :status)
                    """,
            countQuery = """
                    SELECT COUNT(DISTINCT opportunity.id)
                    FROM VolunteerOpportunity opportunity
                    LEFT JOIN opportunity.categories category
                    WHERE (:search IS NULL OR
                           LOWER(opportunity.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
                           LOWER(opportunity.description) LIKE LOWER(CONCAT('%', :search, '%')))
                      AND (:country IS NULL OR LOWER(opportunity.country) = LOWER(:country))
                      AND (:city IS NULL OR LOWER(opportunity.city) = LOWER(:city))
                      AND (:modality IS NULL OR opportunity.modality = :modality)
                      AND (:categoryId IS NULL OR category.id = :categoryId)
                      AND (:organizationId IS NULL OR opportunity.organization.id = :organizationId)
                      AND (:status IS NULL OR opportunity.status = :status)
                    """
    )
    Page<VolunteerOpportunity> search(
            @Param("search") String search,
            @Param("country") String country,
            @Param("city") String city,
            @Param("modality") String modality,
            @Param("categoryId") Long categoryId,
            @Param("organizationId") Long organizationId,
            @Param("status") String status,
            Pageable pageable
    );
}
