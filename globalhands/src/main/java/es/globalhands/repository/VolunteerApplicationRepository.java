package es.globalhands.repository;

import es.globalhands.model.User;
import es.globalhands.model.VolunteerApplication;
import es.globalhands.model.VolunteerOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerApplicationRepository extends JpaRepository<VolunteerApplication, Long> {

    boolean existsByUserAndOpportunity(User user, VolunteerOpportunity opportunity);

    List<VolunteerApplication> findByUser_IdOrderBySubmittedAtDesc(Long userId);
}
