package br.com.authorizationserver.token.store.dao;

import br.com.authorizationserver.token.store.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository<T extends Token> extends JpaRepository<T, Long> {}
