package com.benratti.openpp.api.rest;

import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;
import com.benratti.openpp.api.services.EstimateSessionService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/estimates")
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
    @GetMapping("/")
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





}
