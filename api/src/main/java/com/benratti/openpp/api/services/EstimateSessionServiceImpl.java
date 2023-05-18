package com.benratti.openpp.api.services;

import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EstimateSessionServiceImpl implements EstimateSessionService{

    private final JdbcTemplate template;

    private final RowMapper<EstimateSession> estimateSessionRowMapper = (rs, rowNom) -> new EstimateSession(
                    rs.getString("uid"),
                    rs.getString("name"),
                    rs.getInt("capacity"));


    public EstimateSessionServiceImpl(JdbcTemplate template) {
        this.template = template;
    }

    public Collection<EstimateSession> all() {
        return this.template.query("select * from estimate_session", this.estimateSessionRowMapper);
    }

    public EstimateSession byUID(String uid) throws ResourceNotFoundException {
        var estimateSessionList = this.template.query("select * from estimate_session where uid=?", this.estimateSessionRowMapper, uid);
        if (estimateSessionList.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return estimateSessionList.get(0);
    }

    public Collection<EstimateSession> byName(String name) {
        return this.template.query("select * from estimate_session where name=?", this.estimateSessionRowMapper, name);
    }
}
