package br.com.authorizationserver.token.store.dao;

import br.com.authorizationserver.token.store.model.OpaqueToken;

public interface OpaqueTokenRepository extends TokenRepository<OpaqueToken> {

  OpaqueToken findOneByValue(String value);

  OpaqueToken findOneByValueAndRefreshToken(String value, boolean refreshToken);

}
