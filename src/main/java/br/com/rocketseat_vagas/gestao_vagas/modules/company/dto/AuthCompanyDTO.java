package br.com.rocketseat_vagas.gestao_vagas.modules.company.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCompanyDTO {
  private String password;
  private String username;
  private List<String> roles;
}
