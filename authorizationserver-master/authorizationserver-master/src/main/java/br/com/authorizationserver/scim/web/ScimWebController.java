package br.com.authorizationserver.scim.web;

import br.com.authorizationserver.scim.service.ScimService;
import br.com.authorizationserver.scim.api.resource.ScimUserListResource;
import br.com.authorizationserver.scim.api.resource.mapper.ScimUserListResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.authorizationserver.scim.api.ScimUserRestController.USER_ENDPOINT;

@Controller
public class ScimWebController {

    private final ScimService scimService;
    private final ScimUserListResourceMapper scimUserListResourceMapper;

    @Autowired
    public ScimWebController(ScimService scimService, ScimUserListResourceMapper scimUserListResourceMapper) {
        this.scimService = scimService;
        this.scimUserListResourceMapper = scimUserListResourceMapper;
    }

    @ModelAttribute("allUsers")
    public List<ScimUserListResource> populateUsers() {
        return this.scimService.findAllUsers().stream()
                .map(u -> {
                    URI location =
                            ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path(USER_ENDPOINT + "/{userId}")
                                    .buildAndExpand(u.getIdentifier())
                                    .toUri();
                    return scimUserListResourceMapper.mapEntityToResource(u, location.toASCIIString());
                }).collect(Collectors.toList());
    }

    @GetMapping("/admin/userlist")
    public String findAll() {
        return "userlist";
    }
}
