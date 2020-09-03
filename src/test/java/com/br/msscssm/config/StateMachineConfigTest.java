package com.br.msscssm.config;

import com.br.msscssm.domain.EnumEvent;
import com.br.msscssm.domain.EnumStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<EnumStatus, EnumEvent> factory;

    @Test
    void testNewStateMachine() {
        StateMachine<EnumStatus, EnumEvent> sm = factory.getStateMachine(UUID.randomUUID());

        sm.start();

        System.out.println(sm.getState().toString());

        sm.sendEvent(EnumEvent.SETUP);

        System.out.println(sm.getState().toString());

        sm.sendEvent(EnumEvent.VALIDATE);

        System.out.println(sm.getState().toString());

        sm.sendEvent(EnumEvent.PROCESS);

        System.out.println(sm.getState().toString());

    }
}