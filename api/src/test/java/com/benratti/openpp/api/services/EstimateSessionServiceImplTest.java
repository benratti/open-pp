package com.benratti.openpp.api.services;

import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EstimateSessionServiceImplTest {

    @Autowired
    EstimateSessionServiceImpl service;

    @MockBean
    private JdbcTemplate template;

    @Test
    @DisplayName("all should return estimate session list when sessions exist in database")
    public void all_should_return_estimate_session_list_when_sessions_exist_in_database() {

        List<EstimateSession> expectedEstimateList = asList(
                new EstimateSession("my-uid", "session name", 10),
                new EstimateSession("another-uid", "another session name", 12),
                new EstimateSession("yet another session id", "yet another session name", 90)
        );

        when(template.query(eq("select * from estimate_session"), any(RowMapper.class)))
                .thenReturn(expectedEstimateList);

        Collection<EstimateSession> returnedEstimateSession = service.all();

        assertThat(returnedEstimateSession.size()).isEqualTo(expectedEstimateList.size());
        assertThat(returnedEstimateSession).containsExactlyInAnyOrderElementsOf(expectedEstimateList);

    }

    @Test
    @DisplayName("all should return empty list when no session exists")
    public void all_should_return_empty_list_when_no_session_exists() {

        when(template.query(eq("select * from estimate_session"), any(RowMapper.class)))
                .thenReturn(new ArrayList());

        Collection<EstimateSession> returnedEstimateSession = service.all();

        assertThat(returnedEstimateSession).isEmpty();
    }

    @Test
    @DisplayName("byUID should return estimate session when uid exists")
    public void byUID_should_return_estimate_session_when_uid_exists() {

        List<EstimateSession> estimateSessionList = asList(
                new EstimateSession("my-uid", "session name", 10)
        );

        var expectedEstimateSession = estimateSessionList.get(0);
        when(template.query(
                eq("select * from estimate_session where uid=?"),
                any(RowMapper.class),
                eq("my-uid")
        )).thenReturn(estimateSessionList);

        EstimateSession returnedEstimateSession = service.byUID("my-uid");

        assertThat(returnedEstimateSession).isEqualTo(expectedEstimateSession);

    }

    @Test()
    @DisplayName("byUID should throw resource not found exception when uid does not exist")
    public void byUID_should_throw_resource_not_found_exception_when_uid_does_not_exist() {

        List<EstimateSession> estimateList = asList(
                new EstimateSession("my-uid", "session name", 10),
                new EstimateSession("another-uid", "another session name", 12),
                new EstimateSession("yet another session id", "yet another session name", 90)
        );
        when(template.query(
                eq("select * from estimate_session where uid=?"),
                any(RowMapper.class),
                eq("my-uid")
        )).thenReturn(estimateList);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.byUID("my-uid");
        });
    }

    @Test()
    @DisplayName("byUID should throw resource not found exception when many estimate sessions exist with same uid")
    public void byUID_should_throw_resource_not_found_exception_when_many_estimate_sessions_exist_with_same_uid() {

        when(template.query(
                eq("select * from estimate_session where uid=?"),
                any(RowMapper.class),
                eq("my-uid")
        )).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.byUID("my-uid");
        });
    }
}
