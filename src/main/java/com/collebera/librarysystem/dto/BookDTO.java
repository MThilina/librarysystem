package com.collebera.librarysystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    @NotBlank @NotEmpty @NotNull
    private String isbn;
    @NotBlank @NotEmpty @NotNull
    private String title;
    @NotBlank @NotEmpty @NotNull
    private String author;
}