package com.vanessa.dto;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerRequestDTO {

  @NotBlank(message = "Nome é obrigatório")
  @Length(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
  private String name;

  @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos (somente números)")
  private String cpf;

  @Email(message = "Email inválido")
  private String email;

  @Pattern(regexp = "^\\d{11}$", message = "Telefone deve ter 11 dígitos (DDD + número)")
  private String phone;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
}
