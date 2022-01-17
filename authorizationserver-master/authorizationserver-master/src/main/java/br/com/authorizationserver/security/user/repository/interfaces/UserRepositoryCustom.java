package br.com.authorizationserver.security.user.repository.interfaces;

import br.com.authorizationserver.scim.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryCustom {
    public Optional<User> loadUserByUsername(String userName);
    public Optional<User> findOneByIdentifier(String identifier);
}
