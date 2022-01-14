package br.com.authorizationserver.scim.entities;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    public String identifier;
    public String userName;
    public String familyName;
    public String givenName;
    public Boolean active;
    public String password;
    public String email;
    public String groups;
    public String roles;
}
