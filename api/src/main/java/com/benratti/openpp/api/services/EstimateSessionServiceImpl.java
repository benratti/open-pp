package com.benratti.openpp.api.services;

import com.benratti.openpp.api.data.entities.EstimateSessionRepository;
import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class EstimateSessionServiceImpl implements EstimateSessionService {

    @Autowired
    private EstimateSessionRepository repository;

    @Override
    public Collection<EstimateSession> all() {
        var sessions = new ArrayList<EstimateSession>();
        repository.findAll()
                .forEach(entity ->
                        sessions.add(
                                new EstimateSession(entity.getUid(), entity.getName(), entity.getCapacity())
                        )
                );
        return sessions;
    }

    @Override
    public EstimateSession byUID(String uid) throws ResourceNotFoundException {
        var optionalEntity = repository.findById(uid);
        if (optionalEntity.isPresent()) {
            var entity = optionalEntity.get();
            return new EstimateSession(entity.getUid(), entity.getName(), entity.getCapacity());
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public String create(EstimateSession session) {
        return null;
    }
}
