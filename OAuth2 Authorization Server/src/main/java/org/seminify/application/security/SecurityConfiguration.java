package org.seminify.application.security;

import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  JdbcOAuth2AuthorizationConsentService jdbcOAuth2AuthorizationConsentService(
    JdbcOperations jdbcOperations,
    RegisteredClientRepository registeredClientRepository
  ) {
    return new JdbcOAuth2AuthorizationConsentService(
      jdbcOperations,
      registeredClientRepository
    );
  }

  @Bean
  JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService(
    JdbcOperations jdbcOperations,
    JdbcRegisteredClientRepository jdbcRegisteredClientRepository
  ) {
    return new JdbcOAuth2AuthorizationService(
      jdbcOperations,
      jdbcRegisteredClientRepository
    );
  }

  @Bean
  JdbcRegisteredClientRepository jdbcRegisteredClientRepository(
    JdbcOperations jdbcOperations
  ) {
    return new JdbcRegisteredClientRepository(jdbcOperations);
  }

  @Bean
  OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
    return context -> {
      if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
        context
          .getClaims()
          .claim(
            "authorities",
            context
              .getPrincipal()
              .getAuthorities()
              .stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList())
          );
      }
    };
  }

  @Bean
  JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }
}
