package com.benratti.openpp.api.data.entities;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface EstimateSessionRepository extends CrudRepository<EstimateSessionEntity,String> {

    Collection<EstimateSessionEntity> findByName(String name);

}
