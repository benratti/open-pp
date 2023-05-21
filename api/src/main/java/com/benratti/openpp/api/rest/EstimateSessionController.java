package com.benratti.openpp.api.rest;

import com.benratti.openpp.api.models.EstimateSession;
import com.benratti.openpp.api.services.EstimateSessionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(
        value = "/estimates"
)

public class EstimateSessionController {

    private final EstimateSessionService service;

    public EstimateSessionController(EstimateSessionService service) {
        this.service = service;
    }

    /**
     * This method should return all open estimate session available for
     * connected user.
     * @return list of estimate session
     */
    @GetMapping
    Collection<EstimateSession> getAll() {
        return service.all();
    };

    /**
     * This method return the specified th
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    EstimateSession get(@PathVariable String id)  {
        return service.byUID(id);
    };

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    ServerResponse post(@RequestBody EstimateSession session) {
    //    state( session.uid() == null || session.uid().isBlank(), "uid must not be specified!");
    //    state( session.name() != null && );
        String uid = service.create(session);

        return ServerResponse.created(URI.create("/estimates/" + uid)).build();
    }



}
