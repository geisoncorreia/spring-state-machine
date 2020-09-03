package com.br.msscssm.config;

import com.br.msscssm.domain.RegrasListarMeioEvent;
import com.br.msscssm.domain.RegrasListarMeioStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<RegrasListarMeioStatus, RegrasListarMeioEvent> factory;

    @Test
    void testNewStateMachine() {
        StateMachine<RegrasListarMeioStatus, RegrasListarMeioEvent> sm = factory.getStateMachine(UUID.randomUUID());

        sm.start();

        System.out.println(sm.getState().toString());

        sm.sendEvent(RegrasListarMeioEvent.SETUP);

        System.out.println(sm.getState().toString());

        sm.sendEvent(RegrasListarMeioEvent.VALIDATE);

        System.out.println(sm.getState().toString());

        sm.sendEvent(RegrasListarMeioEvent.PROCESS);

        System.out.println(sm.getState().toString());

    }
}