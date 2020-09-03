package com.br.msscssm.config;

import com.br.msscssm.domain.Proposta;
import com.br.msscssm.domain.RegrasListarMeioStatus;
import com.br.msscssm.domain.RegrasListarMeioEvent;
import com.br.msscssm.domain.action.PropostaAction;
import com.br.msscssm.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt on 2019-07-23.
 */
@Slf4j
@EnableStateMachineFactory
@Configuration
@RequiredArgsConstructor
public class StateMachineConfig extends StateMachineConfigurerAdapter<RegrasListarMeioStatus, RegrasListarMeioEvent> {

    private PropostaRepository propostaRepository;

    @Override
    public void configure(StateMachineStateConfigurer<RegrasListarMeioStatus, RegrasListarMeioEvent> states) throws Exception {
        states.withStates()
                .initial(RegrasListarMeioStatus.SETUP, teste(states))
                .states(EnumSet.allOf(RegrasListarMeioStatus.class))
                .end(RegrasListarMeioStatus.VALIDATE)
                .end(RegrasListarMeioStatus.PROCESS);
    }

    private Action<RegrasListarMeioStatus, RegrasListarMeioEvent> teste(StateMachineStateConfigurer<RegrasListarMeioStatus, RegrasListarMeioEvent> states) {

        /*final UUID uuid = UUID.randomUUID();
        final var proposta = propostaRepository.findById(uuid);*/
        String msn = "teste: " + RegrasListarMeioStatus.SETUP;
        return context -> System.out.println(msn);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RegrasListarMeioStatus, RegrasListarMeioEvent> transitions) throws Exception {
        transitions.withExternal().source(RegrasListarMeioStatus.SETUP)
                .target(RegrasListarMeioStatus.VALIDATE)
                .event(RegrasListarMeioEvent.VALIDATE)
                .action(action1())
                .and()
                .withExternal().source(RegrasListarMeioStatus.VALIDATE)
                .target(RegrasListarMeioStatus.PROCESS)
                .action(teste2(transitions));
    }

    private Action<RegrasListarMeioStatus, RegrasListarMeioEvent> teste2(StateMachineTransitionConfigurer<RegrasListarMeioStatus, RegrasListarMeioEvent> transitions) {
        String msn = "teste" + RegrasListarMeioStatus.PROCESS;
        return context -> System.out.println(msn);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<RegrasListarMeioStatus, RegrasListarMeioEvent> config) throws Exception {
        StateMachineListenerAdapter<RegrasListarMeioStatus, RegrasListarMeioEvent> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<RegrasListarMeioStatus, RegrasListarMeioEvent> from, State<RegrasListarMeioStatus, RegrasListarMeioEvent> to) {
                log.info(String.format("stateChanged(from: %s, to: %s)", from, to));
            }
        };

        config.withConfiguration()
                .listener(adapter);
    }

    @Bean
    public Action<RegrasListarMeioStatus, RegrasListarMeioEvent> action1() {
        return new Action<RegrasListarMeioStatus, RegrasListarMeioEvent>() {

            @Override
            public void execute(StateContext<RegrasListarMeioStatus, RegrasListarMeioEvent> context) {
                System.out.println("Teste Ação: " + context.getEvent());
            }
        };
    }
}
