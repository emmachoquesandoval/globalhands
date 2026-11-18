package es.globalhands.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "solicitudes",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_solicitudes_usuario_voluntariado",
                columnNames = {"usuario_id", "voluntariado_id"}
        )
)
public class VolunteerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "voluntariado_id", nullable = false)
    private VolunteerOpportunity opportunity;

    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String message;

    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    @Column(name = "estado", nullable = false, length = 20)
    private String status;

    protected VolunteerApplication() {
    }

    public VolunteerApplication(User user, VolunteerOpportunity opportunity, String message) {
        this.user = user;
        this.opportunity = opportunity;
        this.message = message;
    }

    @PrePersist
    void setSubmissionDefaults() {
        if (submittedAt == null) {
            submittedAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "pendiente";
        }
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public VolunteerOpportunity getOpportunity() {
        return opportunity;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getStatus() {
        return status;
    }
}
