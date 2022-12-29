package dev.panasovsky.module.auth.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class RoleSavingEvent {

    private final String rolename;

}