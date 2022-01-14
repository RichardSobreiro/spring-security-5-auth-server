package br.com.authorizationserver.token.store.dao;

import br.com.authorizationserver.token.store.model.JsonWebToken;

public interface JsonWebTokenRepository extends TokenRepository<JsonWebToken> {

  JsonWebToken findOneByValue(String value);

  JsonWebToken findOneByValueAndAccessToken(String value, boolean accessToken);
}
