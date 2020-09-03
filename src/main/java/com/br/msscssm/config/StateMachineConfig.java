package com.br.msscssm.config;

import com.br.msscssm.domain.EnumStatus;
import com.br.msscssm.domain.EnumEvent;
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

@Slf4j
@EnableStateMachineFactory
@Configuration
@RequiredArgsConstructor
public class StateMachineConfig extends StateMachineConfigurerAdapter<EnumStatus, EnumEvent> {

    private PropostaRepository propostaRepository;

    @Override
    public void configure(StateMachineStateConfigurer<EnumStatus, EnumEvent> states) throws Exception {
        states.withStates()
                .initial(EnumStatus.SETUP, teste(states))
                .states(EnumSet.allOf(EnumStatus.class))
                .end(EnumStatus.VALIDATE)
                .end(EnumStatus.PROCESS);
    }

    private Action<EnumStatus, EnumEvent> teste(StateMachineStateConfigurer<EnumStatus, EnumEvent> states) {

        /*final UUID uuid = UUID.randomUUID();
        final var proposta = propostaRepository.findById(uuid);*/
        String msn = "teste: " + EnumStatus.SETUP;
        return context -> System.out.println(msn);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EnumStatus, EnumEvent> transitions) throws Exception {
        transitions.withExternal().source(EnumStatus.SETUP)
                .target(EnumStatus.VALIDATE)
                .event(EnumEvent.VALIDATE)
                .action(action1())
                .and()
                .withExternal().source(EnumStatus.VALIDATE)
                .target(EnumStatus.PROCESS)
                .action(teste2(transitions));
    }

    private Action<EnumStatus, EnumEvent> teste2(StateMachineTransitionConfigurer<EnumStatus, EnumEvent> transitions) {
        String msn = "teste" + EnumStatus.PROCESS;
        return context -> System.out.println(msn);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<EnumStatus, EnumEvent> config) throws Exception {
        StateMachineListenerAdapter<EnumStatus, EnumEvent> adapter = new StateMachineListenerAdapter<>(){
            @Override
            public void stateChanged(State<EnumStatus, EnumEvent> from, State<EnumStatus, EnumEvent> to) {
                log.info(String.format("stateChanged(from: %s, to: %s)", from, to));
            }
        };

        config.withConfiguration()
                .listener(adapter);
    }

    @Bean
    public Action<EnumStatus, EnumEvent> action1() {
        return new Action<EnumStatus, EnumEvent>() {

            @Override
            public void execute(StateContext<EnumStatus, EnumEvent> context) {
                System.out.println("Teste Ação: " + context.getEvent());
            }
        };
    }
}
