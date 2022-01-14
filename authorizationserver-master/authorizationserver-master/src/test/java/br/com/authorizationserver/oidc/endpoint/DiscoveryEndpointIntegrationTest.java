package br.com.authorizationserver.oidc.endpoint;

import br.com.authorizationserver.annotation.WebIntegrationTest;
import br.com.authorizationserver.oauth.endpoint.AuthorizationEndpoint;
import br.com.authorizationserver.oidc.endpoint.discovery.Discovery;
import br.com.authorizationserver.oidc.endpoint.discovery.DiscoveryEndpoint;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

@WebIntegrationTest
class DiscoveryEndpointIntegrationTest {

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach
  void initMockMvc() {
    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
  }

  @Test
  void discoveryEndpoint() {
    Discovery discovery =
        given()
            .when()
            .get(DiscoveryEndpoint.ENDPOINT)
            .then()
            .log()
            .ifValidationFails()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(not(empty()))
            .extract()
            .as(Discovery.class);
    assertThat(discovery).isNotNull();
    assertThat(discovery.getAuthorization_endpoint())
        .isEqualTo("http://localhost:8080/auth" + AuthorizationEndpoint.ENDPOINT);
  }
}
