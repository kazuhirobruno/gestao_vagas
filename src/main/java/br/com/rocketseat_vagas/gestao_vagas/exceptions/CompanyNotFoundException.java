package br.com.rocketseat_vagas.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException() {
    super("Company not found");
  }
}
