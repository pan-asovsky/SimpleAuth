package dev.panasovsky.module.auth.controllers;

import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.services.RoleService;
import dev.panasovsky.module.auth.util.JsonConvertHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MainController {

    private final RoleService roleService;

    private final static String hello = "hello!";
    private final static String success = "success";


    // TODO: явное указание роли при регистрации доступно всем ролям!
    // TODO: выдавать токен при регистрации?
    // TODO: выдача JSON методами register(), helloUser() и helloAdmin()
    // TODO: разобраться с SecurityConfig#filterChain()


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/api/hello/user")
    public ResponseEntity<JsonNode> helloUser() {

        final JsonNode result = JsonConvertHelper.getJsonResponse(success, hello);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/hello/admin")
    public ResponseEntity<JsonNode> helloAdmin() {

        final JsonNode result = JsonConvertHelper.getJsonResponse(success, hello);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/api/admin/addrole")
    public ResponseEntity<JsonNode> addRole(@RequestBody final Role role) {

        final JsonNode result = roleService.save(role);
        return ResponseEntity.ok(result);
    }

}