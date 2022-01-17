package br.com.authorizationserver.scim.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.UUID;

@Document(collection = "users")
public class User {
    @Id
    @Field("_id")
    public String identifier;
    public String userName;
    public String familyName;
    public String givenName;
    public Boolean active;
    public String password;
    public String email;
    public ArrayList<String> roles;
}
