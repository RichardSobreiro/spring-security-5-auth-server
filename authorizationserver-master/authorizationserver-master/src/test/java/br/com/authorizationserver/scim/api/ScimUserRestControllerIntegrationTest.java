package br.com.authorizationserver.scim.api;

import br.com.authorizationserver.scim.api.resource.CreateScimUserResource;
import br.com.authorizationserver.scim.api.resource.ScimUserResource;
import br.com.authorizationserver.scim.api.resource.mapper.*;
import br.com.authorizationserver.scim.model.ScimEmailEntity;
import br.com.authorizationserver.scim.model.ScimGroupEntity;
import br.com.authorizationserver.scim.model.ScimUserEntity;
import br.com.authorizationserver.scim.model.ScimUserGroupEntity;
import br.com.authorizationserver.scim.service.ScimService;
import br.com.authorizationserver.security.client.RegisteredClientDetailsService;
import br.com.authorizationserver.security.user.EndUserDetails;
import br.com.authorizationserver.security.user.EndUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.emptySet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScimUserRestController.class)
class ScimUserRestControllerIntegrationTest {

    @MockBean
    private EndUserDetailsService endUserDetailsService;

    @MockBean
    private RegisteredClientDetailsService registeredClientDetailsService;

    @MockBean
    private ScimService scimService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        ScimUserResourceMapper scimUserResourceMapper() {
            return new ScimUserResourceMapper();
        }

        @Bean
        ScimUserListResourceMapper scimUserListResourceMapper() {
            return new ScimUserListResourceMapper();
        }

        @Bean
        ScimGroupResourceMapper scimGroupResourceMapper() {
            return new ScimGroupResourceMapper();
        }

        @Bean
        ScimGroupListResourceMapper scimGroupListResourceMapper() {
            return new ScimGroupListResourceMapper();
        }

        @Bean
        CreateScimUserResourceMapper createScimUserResourceMapper() {
            return new CreateScimUserResourceMapper();
        }

    }

    @BeforeEach
    public void setUp(
            WebApplicationContext webApplicationContext) {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .build();
    }

    @Test
    void getUsers() throws Exception {
        ScimUserEntity scimUserEntity = new ScimUserEntity(
                UUID.randomUUID(),
                "mmuster",
                "Muster",
                "Max",
                true,
                "secret4test",
                Set.of(new ScimEmailEntity("test@example.com", "work", true)),
                null,
                Set.of("USER"));
        scimUserEntity.setGroups(
                Set.of(
                        new ScimUserGroupEntity(scimUserEntity,
                                new ScimGroupEntity(UUID.randomUUID(), "123", "test_group", null))));
        ReflectionTestUtils.setField(scimUserEntity, "version", 1L);
        given(scimService.findAllUsers())
                .willReturn(
                        List.of(scimUserEntity));
        mockMvc
                .perform(get(ScimUserRestController.USER_ENDPOINT))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getUser() throws Exception {
        UUID userIdentifier = UUID.randomUUID();
        ScimUserEntity scimUserEntity = new ScimUserEntity(
                userIdentifier,
                "mmuster",
                "Muster",
                "Max",
                true,
                "secret4test",
                Set.of(new ScimEmailEntity("test@example.com", "work", true)),
                null,
                Set.of("USER"));
        scimUserEntity.setGroups(
                Set.of(
                        new ScimUserGroupEntity(scimUserEntity,
                                new ScimGroupEntity(UUID.randomUUID(), "123", "test_group", null))));
        ReflectionTestUtils.setField(scimUserEntity, "version", 1L);
        given(scimService.findUserByIdentifier(userIdentifier))
                .willReturn(
                        Optional.of(scimUserEntity));
        mockMvc
                .perform(get(ScimUserRestController.USER_ENDPOINT + "/{userid}", userIdentifier))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void authenticatedUser() throws Exception {
        UUID userIdentifier = UUID.randomUUID();
        ScimUserEntity scimUserEntity = new ScimUserEntity(
                userIdentifier,
                "mmuster",
                "Muster",
                "Max",
                true,
                "secret4test",
                Set.of(new ScimEmailEntity("test@example.com", "work", true)),
                null,
                Set.of("USER"));
        scimUserEntity.setGroups(
                Set.of(
                        new ScimUserGroupEntity(scimUserEntity,
                                new ScimGroupEntity(UUID.randomUUID(), "123", "test_group", null))));
        ReflectionTestUtils.setField(scimUserEntity, "version", 1L);
        EndUserDetails endUserDetails = new EndUserDetails(scimUserEntity);
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(endUserDetails, "secret4test"));
        given(scimService.findUserByIdentifier(userIdentifier))
                .willReturn(
                        Optional.of(scimUserEntity));
        mockMvc
                .perform(get(ScimUserRestController.ME_ENDPOINT))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void createUser() throws Exception {
        UUID userIdentifier = UUID.randomUUID();
        ScimUserEntity scimUserEntity = new ScimUserEntity(
                userIdentifier,
                "mmuster",
                "Muster",
                "Max",
                true,
                "secret4test",
                Set.of(new ScimEmailEntity("test@example.com", "work", true)),
                emptySet(),
                Set.of("USER"));
        scimUserEntity.setGroups(
                Set.of(
                        new ScimUserGroupEntity(scimUserEntity,
                                new ScimGroupEntity(UUID.randomUUID(), "123", "test_group", null))));
        ReflectionTestUtils.setField(scimUserEntity, "version", 1L);
        given(scimService.createUser(any())).willReturn(scimUserEntity);

        CreateScimUserResource createScimUserResource = new CreateScimUserResourceMapper().mapEntityToResource(scimUserEntity);
        mockMvc
                .perform(
                        post(ScimUserRestController.USER_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createScimUserResource)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateUser() throws Exception {
        UUID userIdentifier = UUID.randomUUID();
        ScimUserEntity scimUserEntity = new ScimUserEntity(
                userIdentifier,
                "mmuster",
                "Muster",
                "Max",
                true,
                "secret4test",
                Set.of(new ScimEmailEntity("test@example.com", "work", true)),
                null,
                Set.of("USER"));
        scimUserEntity.setGroups(
                Set.of(
                        new ScimUserGroupEntity(scimUserEntity,
                                new ScimGroupEntity(UUID.randomUUID(), "123", "test_group", null))));
        ReflectionTestUtils.setField(scimUserEntity, "version", 1L);
        given(scimService.findUserByIdentifier(userIdentifier))
                .willReturn(
                        Optional.of(scimUserEntity));
        given(scimService.updateUser(any(), any())).willReturn(scimUserEntity);

        RequestContextHolder.setRequestAttributes(new ServletWebRequest(new MockHttpServletRequest()));

        ScimUserResource scimUserResource = new ScimUserResourceMapper().mapEntityToResource(scimUserEntity,
                "http://localhost:9090/api/Users/" + userIdentifier);

        mockMvc
                .perform(
                        put(ScimUserRestController.USER_ENDPOINT + "/{userid}", userIdentifier)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(scimUserResource)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteUser() throws Exception {
        UUID userIdentifier = UUID.randomUUID();
        mockMvc
                .perform(delete(ScimUserRestController.USER_ENDPOINT + "/{userid}", userIdentifier))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
