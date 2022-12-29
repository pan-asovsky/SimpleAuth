package dev.panasovsky.module.auth.services;

import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.util.JsonConvertHelper;
import dev.panasovsky.module.auth.events.RoleSavingEvent;
import dev.panasovsky.module.auth.repositories.RoleRepository;
import dev.panasovsky.module.auth.exceptions.RegistrationException;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.ApplicationEventPublisher;

import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher publisher;


    public Role findByRolename(final String rolename) {
        return roleRepository.findByRolename(rolename);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public JsonNode save(final Role role) {

        final String error = "error";
        final String success = "success";
        final String saved = "role saved";

        try {
            saveRole(role);
            log.debug("Saved new role: {}", role.getRolename());
            publisher.publishEvent(new RoleSavingEvent(role.getRolename()));
        } catch (final RegistrationException e) {
            log.debug("attempt to keep an existing role: {}", role.getRolename());
            return JsonConvertHelper.getJsonResponse(error, e.getMessage());
        }

        return JsonConvertHelper.getJsonResponse(success, saved);
    }

    private void saveRole(final Role role) throws RegistrationException {

        if (findByRolename(role.getRolename()) == null) roleRepository.save(role);
        else throw new RegistrationException("this role exists");
    }

}

