package com.example.productservice.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "buyers")
public class Buyer extends AbstractEntity<Integer> {

    private static final long serialVersionUID = -8990226958452772812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @Column(name = "email", unique = true, length = 255, nullable = false)
    private String email;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @Column(name = "surname", length = 255, nullable = false)
    private String surname;

    @Version
    @Column(nullable = false)
    private Integer version;

    Buyer() {
        // do nothing for hydration purpose
    }

    public Buyer(String email, String name, String surname) {
        this.email = Objects.requireNonNull(email, "email can not be null");
        this.name = Objects.requireNonNull(name, "name can not be null");
        this.surname = Objects.requireNonNull(surname, "surname can not be null");
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
