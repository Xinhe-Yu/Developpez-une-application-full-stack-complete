package com.openclassrooms.mddapi.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestBodyLoggingFilter extends OncePerRequestFilter {
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(RequestBodyLoggingFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

    String body = new String(cachedBodyHttpServletRequest.getInputStream()
        .readAllBytes(), StandardCharsets.UTF_8);
    log.info("Request Body: {}", body);

    chain.doFilter(cachedBodyHttpServletRequest, response);
  }
}
