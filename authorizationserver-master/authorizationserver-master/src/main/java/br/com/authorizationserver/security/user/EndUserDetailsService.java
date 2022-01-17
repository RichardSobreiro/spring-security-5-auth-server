package br.com.authorizationserver.security.user;

import br.com.authorizationserver.security.user.repository.interfaces.UserRepositoryCustom;
import br.com.authorizationserver.scim.entities.User;
import br.com.authorizationserver.scim.model.ScimUserEntity;
import br.com.authorizationserver.scim.service.ScimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Qualifier("endUserDetailsService")
@Service
public class EndUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;

    private final ScimService scimService;

    public EndUserDetailsService(ScimService scimService) {
        this.scimService = scimService;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepositoryCustom.loadUserByUsername(username);
        if (user != null){
            Optional<ScimUserEntity> scimUserMongoDB = user.map(ScimUserEntity::new);
            return scimUserMongoDB.map(EndUserDetails::new).
                    orElseThrow(() -> new UsernameNotFoundException("No user found"));
        } else {
            Optional<ScimUserEntity> scimUserInMemory = this.scimService.findUserByUserName(username);

            EndUserDetails endUserDetailsInMemory = scimUserInMemory.map(EndUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("No user found"));

            return this.scimService
                    .findUserByUserName(username)
                    .map(EndUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("No user found"));
        }
    }
}
