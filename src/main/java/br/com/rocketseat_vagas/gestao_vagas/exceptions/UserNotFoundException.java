package br.com.rocketseat_vagas.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found");
  }

}
