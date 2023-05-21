package com.benratti.openpp.api.rest;

import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;
import com.benratti.openpp.api.services.EstimateSessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = EstimateSessionController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = HttpEncodingAutoConfiguration.class)
)
public class EstimateSessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EstimateSessionServiceImpl service;

    @Test
    @DisplayName("GET /estimates should return HTTP 200 and all estimate sessions when estimate sessions exist")
    public void getAll_should_return_all_estimate_sessions_when_estimate_sessions_exist() throws Exception {
        List<EstimateSession> expectedEstimateList = asList(
                new EstimateSession("my-uid", "session name", 10),
                new EstimateSession("another-uid", "another session name", 12),
                new EstimateSession("yet another session id", "yet another session name", 90)
        );
        when(service.all()).thenReturn(expectedEstimateList);

        var result = mockMvc.perform(get("/estimates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var jsonContent = result.getResponse().getContentAsString();
        List<EstimateSession> returnedEstimateList = mapper.readValue(
                jsonContent,
                mapper.getTypeFactory().constructCollectionType(List.class, EstimateSession.class));

        assertThat(returnedEstimateList.size()).isEqualTo(expectedEstimateList.size());
        assertThat(returnedEstimateList).containsExactlyInAnyOrderElementsOf(expectedEstimateList);

    }

    @Test
    @DisplayName("GET /estimates should return HTTP 200 and empty list when no estimate session exists")
    public void getAll_should_return_empty_list_when_no_estimate_session_exists() throws Exception {
        when(service.all()).thenReturn(new ArrayList<>());

        var result = mockMvc.perform(get("/estimates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var jsonContent = result.getResponse().getContentAsString();
        List<EstimateSession> returnedEstimateList = mapper.readValue(
                jsonContent,
                mapper.getTypeFactory().constructCollectionType(List.class, EstimateSession.class));

        assertThat(returnedEstimateList).isEmpty();

    }

    @Test
    @DisplayName("GET /estimates/id should return HTTP 200 and estimate session when estimate session exists")
    public void get_should_return_estimate_session_when_estimate_session_exists() throws Exception {
        var expectedEstimateSession = new EstimateSession("my-uid", "session name", 10);

        when(service.byUID("my-uid")).thenReturn(expectedEstimateSession);

        var result = mockMvc.perform(get("/estimates/my-uid").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var jsonContent = result.getResponse().getContentAsString();
        EstimateSession returnedEstimateSession = mapper.readValue(jsonContent, EstimateSession.class);

        assertThat(returnedEstimateSession).isEqualTo(expectedEstimateSession);

    }

    @Test
    @DisplayName("GET /estimates/id should return HTTP 404 when estimate session does not exist")
    public void get_should_return_problem_detail_when_estimate_session_does_not_exist() throws Exception {
        when(service.byUID("my-uid")).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/estimates/my-uid"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andExpect(jsonPath("$.type").value("https://api.open-pp.com/errors/illegal-state"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.instance").value("/estimates/my-uid"));

    }



    @Disabled("Disable until problem with mockmvc set wrong content type")
    @Test
    @DisplayName("POST /estimates should return HTTP with location when estimate session is created")
    public void post_should_return_created_resource_location_when_estimate_session_is_created() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        var jsonBodyRequest = "{ " +
                "\"name\": \"session name\"," +
                "\"capacity\": 10" +
                "}";

        EstimateSession session = new EstimateSession(null, "session name", 10);

        String uid = "resource-uid";
        when(service.create(eq(session))).thenReturn(uid);

        mockMvc.perform(post("/estimates")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(session))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.ISO_8859_1)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }
}
