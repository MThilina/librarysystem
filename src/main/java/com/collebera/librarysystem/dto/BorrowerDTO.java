package com.collebera.librarysystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowerDTO {
    private Long id;
    @NotBlank @NotEmpty @NotNull
    private String name;
    @NotBlank @NotEmpty @NotNull
    private String email;
}
