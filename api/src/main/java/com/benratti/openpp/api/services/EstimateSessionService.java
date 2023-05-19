package com.benratti.openpp.api.services;

import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;

import java.util.Collection;

public interface EstimateSessionService {


    Collection<EstimateSession> all();

    EstimateSession byUID(String uid) throws ResourceNotFoundException;

}
