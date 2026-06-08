package com.centrotreinamento.bjj.dto.request;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlunoRequestDTO(
    @NotBlank @Size(min = 3, max = 100) String nome,
    @Min(4) @Max(90) int idade,
    @NotBlank @Email String email,
    @NotBlank @Pattern(regexp = "\\d{11}", message = "Telefone deve contar 11 dígitos") String telefone) {
}
