package com.example.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class BuyerDTO {
    private Integer id;

    @Schema(description = "Buyer's Email", example = "email1@email.com", required = true)
    @Email
    private String email;

    @Schema(description = "Buyer's Name", example = "name1", required = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(description = "Buyer's Surname", example = "surname1", required = true)
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
