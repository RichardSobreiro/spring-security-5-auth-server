package br.com.authorizationserver.scim.service;

import java.util.UUID;

public class ScimGroupNotFoundException extends RuntimeException {

    public ScimGroupNotFoundException(UUID groupIdentifier) {
        super(String.format("No group found with identifier %s", groupIdentifier));
    }
}
