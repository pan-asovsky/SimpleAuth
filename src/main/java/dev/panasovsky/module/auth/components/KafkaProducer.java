package dev.panasovsky.module.auth.components;

import dev.panasovsky.module.auth.model.Role;
import dev.panasovsky.module.auth.services.RoleService;
import dev.panasovsky.module.auth.events.RoleSavingEvent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;


@Log4j2
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final static String ROLE_TOPIC = "roles";

    private final RoleService roleService;
    private final KafkaTemplate<String, JsonNode> kafkaTemplate;


    // TODO: переработать механизм вызова метода отправки сообщения
    @EventListener(ApplicationReadyEvent.class)
    public void sendMessage() {
        sendMessageAboutChangingRoleList();
    }

    // TODO: обработка callback
    @EventListener(RoleSavingEvent.class)
    public void sendMessageAboutChangingRoleList() {

        final Set<Role> roles = new HashSet<>(getAllRolesFromRepository());
        final JsonNode result = setToJson(roles);

        final String key = "auth";
        final ListenableFuture<SendResult<String, JsonNode>> future =
                kafkaTemplate.send(ROLE_TOPIC, key, result);
        future.addCallback(e -> log.debug("kafka says: {}", Objects.requireNonNull(e).getProducerRecord()),
                ex -> log.error("error at kafka: {}", ex.getLocalizedMessage()));
        kafkaTemplate.flush();
    }

    private List<Role> getAllRolesFromRepository() {
        return roleService.findAll();
    }

    // TODO: обработка исключений
    // по текущему сценарию исключения выбрасываться не должны,
    // поэтому пока оставляю так.
    @SneakyThrows
    private JsonNode setToJson(final Set<?> value) {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        final String jsonString = mapper.writeValueAsString(value);
        return mapper.readTree(jsonString);
    }

}