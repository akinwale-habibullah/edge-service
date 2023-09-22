package com.akinwalehabib.edgeservice.config;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@WebFluxTest
@Import(SecurityConfig.class)
public class SecurityConfigTests {
  
  @Autowired
  WebTestClient webTestClient;

  @MockBean
  ReactiveClientRegistrationRepository clientRegistrationRepository;

  @Test
  void whenLogoutAuthenticatedAndWithCsrfTokenThen302() {
    when(clientRegistrationRepository.findByRegistrationId("test"))
      .thenReturn(Mono.just(testClientRegistration()));
    
    webTestClient
      .mutateWith(
        SecurityMockServerConfigurers.mockOidcLogin())
      .mutateWith(SecurityMockServerConfigurers.csrf())
      .post()
      .uri("/logout")
      .exchange()
      .expectStatus().isFound();
  }

  private ClientRegistration testClientRegistration() {
    return ClientRegistration.withRegistrationId("test")
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .clientId("test")
      .authorizationUri("https://sso.polarbookship.com/auth")
      .tokenUri("http://sso.polarbookshop.com/token")
      .redirectUri("http://polarbookshop.com")
      .build();
  }
}
