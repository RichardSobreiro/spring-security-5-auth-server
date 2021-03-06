package br.com.authorizationserver.oauth.endpoint.token;

import br.com.authorizationserver.oauth.common.AuthenticationUtil;
import br.com.authorizationserver.oauth.common.ClientCredentials;
import br.com.authorizationserver.oauth.endpoint.token.resource.TokenRequest;
import br.com.authorizationserver.oauth.endpoint.token.resource.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class TokenEndpointHelper {

  private TokenEndpointHelper() {}

  static ClientCredentials retrieveClientCredentials(
      String authorizationHeader, TokenRequest tokenRequest) {
    ClientCredentials clientCredentials = null;
    if (authorizationHeader != null) {
      clientCredentials = AuthenticationUtil.fromBasicAuthHeader(authorizationHeader);
    } else if (StringUtils.isNotBlank(tokenRequest.getClient_id())) {
      clientCredentials =
          new ClientCredentials(tokenRequest.getClient_id(), tokenRequest.getClient_secret());
    }
    return clientCredentials;
  }

  static ResponseEntity<TokenResponse> reportUnauthorizedClientError() {
    return ResponseEntity.badRequest().body(new TokenResponse("unauthorized_client"));
  }

  static ResponseEntity<TokenResponse> reportInvalidClientError() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .header("WWW-Authenticate", "Basic")
        .body(new TokenResponse("invalid_client"));
  }

  static ResponseEntity<TokenResponse> reportInvalidGrantError() {
    return ResponseEntity.badRequest().body(new TokenResponse("invalid_grant"));
  }
}
