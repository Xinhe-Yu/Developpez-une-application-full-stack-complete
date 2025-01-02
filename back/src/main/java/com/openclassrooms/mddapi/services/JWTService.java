package com.openclassrooms.mddapi.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
  private JwtDecoder jwtDecoder;
  private JwtEncoder jwtEncoder;

  public JWTService(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder) {
    this.jwtDecoder = jwtDecoder;
    this.jwtEncoder = jwtEncoder;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    String email = customUserDetails.getEmail();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.DAYS))
        .subject(email)
        .build();
    JwtEncoderParameters parameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
    return this.jwtEncoder.encode(parameters).getTokenValue();
  }

  public String getEmailFromToken(String token) {
    Jwt jwt = jwtDecoder.decode(token);
    return jwt.getClaimAsString("sub");
  }

  public boolean isValidToken(String token) {
    try {
      Jwt jwt = jwtDecoder.decode(token);
      return Instant.now().isBefore(jwt.getExpiresAt());
    } catch (Exception e) {
      return false;
    }
  }
}
