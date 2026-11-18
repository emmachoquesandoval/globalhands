package es.globalhands.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizaciones")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String name;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "telefono", length = 25)
    private String phone;

    @Column(name = "ciudad", length = 100)
    private String city;

    @Column(name = "pais", length = 100)
    private String country;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    protected Organization() {
    }

    public Organization(String name, String description, String email, String phone,
                        String city, String country) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    @PrePersist
    void setRegistrationTime() {
        if (registeredAt == null) {
            registeredAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
