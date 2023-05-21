package com.benratti.openpp.api.services;

import com.benratti.openpp.api.data.entities.EstimateSessionEntity;
import com.benratti.openpp.api.data.entities.EstimateSessionRepository;
import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = EstimateSessionServiceImplTestConfiguration.class)
public class EstimateSessionServiceImplTest {

    @Autowired
    private EstimateSessionServiceImpl service;

    @MockBean
    private EstimateSessionRepository repository;

    @Test
    @DisplayName("all should return estimate session list when sessions exist in database")
    public void all_should_return_estimate_session_list_when_sessions_exist_in_database() {

        List<EstimateSessionEntity> entitiesFromDB = asList(
                new EstimateSessionEntity("my-uid", "session name", 10),
                new EstimateSessionEntity("another-uid", "another session name", 12),
                new EstimateSessionEntity("yet another session id", "yet another session name", 90)
        );

        List<EstimateSession> expectedEstimateList = asList(
                new EstimateSession("my-uid", "session name", 10),
                new EstimateSession("another-uid", "another session name", 12),
                new EstimateSession("yet another session id", "yet another session name", 90)
        );

        when(repository.findAll())
                .thenReturn(entitiesFromDB);

        Collection<EstimateSession> returnedEstimateSession = service.all();

        assertThat(returnedEstimateSession.size()).isEqualTo(expectedEstimateList.size());
        assertThat(returnedEstimateSession).containsExactlyInAnyOrderElementsOf(expectedEstimateList);

    }

    @Test
    @DisplayName("all should return empty list when no session exists")
    public void all_should_return_empty_list_when_no_session_exists() {

        when(repository.findAll())
                .thenReturn(new ArrayList<>());

        Collection<EstimateSession> returnedEstimateSession = service.all();

        assertThat(returnedEstimateSession).isEmpty();

    }

    @Test
    @DisplayName("byUID should return estimate session when uid exists")
    public void byUID_should_return_estimate_session_when_uid_exists() {

        String uid = "my-uid";
        String name = "session name";
        int capacity = 10;

        Optional<EstimateSessionEntity> entity = Optional.of(new EstimateSessionEntity(uid, name, capacity));
        var expectedEstimateSession = new EstimateSession(uid, name, capacity);

        when(repository.findById("my-uid")).thenReturn(entity);

        EstimateSession returnedEstimateSession = service.byUID("my-uid");

        assertThat(returnedEstimateSession).isEqualTo(expectedEstimateSession);

    }

    @Test()
    @DisplayName("byUID should throw resource not found exception when uid does not exist")
    public void byUID_should_throw_resource_not_found_exception_when_uid_does_not_exist() {
        Optional<EstimateSessionEntity> entity = Optional.empty();

        when(repository.findById("my-uid")).thenReturn(entity);

        assertThatThrownBy(() -> service.byUID("my-uid")).isInstanceOf(ResourceNotFoundException.class);
    }

}
