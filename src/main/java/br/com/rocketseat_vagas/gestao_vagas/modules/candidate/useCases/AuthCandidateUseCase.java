package br.com.rocketseat_vagas.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.rocketseat_vagas.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.rocketseat_vagas.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.rocketseat_vagas.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
      throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(
        () -> {
          throw new UsernameNotFoundException("Username/password incorrect");
        });

    var passwordMatches = this.passwordEncoder
        .matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
    var roles = Arrays.asList("CANDIDATE");
    var token = JWT.create()
        .withIssuer("javagas")
        .withSubject(candidate.getId().toString())
        .withClaim("roles", roles)
        .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
        .sign(algorithm);

    var authCandidateResponseDTO = AuthCandidateResponseDTO.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .roles(roles)
        .build();

    return authCandidateResponseDTO;
  }
}
