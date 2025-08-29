package br.com.rocketseat_vagas.gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException {
  public JobNotFoundException() {
    super("Job not found");
  }

}
