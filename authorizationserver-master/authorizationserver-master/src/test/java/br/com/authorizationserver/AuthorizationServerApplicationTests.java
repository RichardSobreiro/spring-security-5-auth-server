package br.com.authorizationserver;

import br.com.authorizationserver.oauth.endpoint.AuthorizationEndpoint;
import br.com.authorizationserver.oauth.endpoint.token.TokenEndpoint;
import br.com.authorizationserver.oidc.endpoint.userinfo.UserInfoEndpoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration-test")
@DirtiesContext
@SpringBootTest
class AuthorizationServerApplicationTests {

    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private UserInfoEndpoint userInfoEndpoint;

    @Test
    @DisplayName("Verify that the authorization server application starts")
    void verifyApplicationStart() {

        assertThat(authorizationEndpoint).isNotNull();
        assertThat(tokenEndpoint).isNotNull();
        assertThat(userInfoEndpoint).isNotNull();

    }

}
