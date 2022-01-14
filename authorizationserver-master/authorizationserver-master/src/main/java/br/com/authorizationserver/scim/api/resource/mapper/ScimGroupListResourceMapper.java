package br.com.authorizationserver.scim.api.resource.mapper;

import br.com.authorizationserver.scim.model.ScimGroupEntity;
import br.com.authorizationserver.scim.api.resource.ScimGroupListResource;
import br.com.authorizationserver.scim.api.resource.ScimMetaResource;
import org.springframework.stereotype.Component;

@Component
public class ScimGroupListResourceMapper {

    public ScimGroupListResource mapEntityToResource(ScimGroupEntity scimGroupEntity, String location) {
        return new ScimGroupListResource(new
                ScimMetaResource("Group", scimGroupEntity.getCreatedDate(),
                scimGroupEntity.getLastModifiedDate(),
                scimGroupEntity.getVersion().toString(), location), scimGroupEntity.getIdentifier(),
                scimGroupEntity.getExternalId(), scimGroupEntity.getDisplayName());
    }

}
