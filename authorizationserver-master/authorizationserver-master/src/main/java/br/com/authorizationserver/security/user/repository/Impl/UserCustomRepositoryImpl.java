package br.com.authorizationserver.security.user.repository.Impl;

import br.com.authorizationserver.security.user.repository.interfaces.UserRepositoryCustom;
import br.com.authorizationserver.scim.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserCustomRepositoryImpl implements UserRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<User> loadUserByUsername(String userName) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(userName));
        User user = mongoTemplate.findOne(query, User.class);
        if(user != null)
            return Optional.of(user);
        else
            return null;
    }

    @Override
    public Optional<User> findOneByIdentifier(String identifier){
        User user = mongoTemplate.findById(identifier, User.class, "users");
        if(user != null)
            return Optional.of(user);
        else
            return null;
    }
}
