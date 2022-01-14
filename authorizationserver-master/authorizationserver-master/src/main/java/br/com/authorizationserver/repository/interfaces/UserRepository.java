package br.com.authorizationserver.repository.interfaces;

import br.com.authorizationserver.scim.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User loadUserByUsername(String userName);
}
