package br.com.authorizationserver.security.user.repository.interfaces;

import br.com.authorizationserver.scim.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
