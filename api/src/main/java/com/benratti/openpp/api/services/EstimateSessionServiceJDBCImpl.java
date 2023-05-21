package com.benratti.openpp.api.services;

import com.benratti.openpp.api.exceptions.ResourceNotFoundException;
import com.benratti.openpp.api.models.EstimateSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;

public class EstimateSessionServiceJDBCImpl implements EstimateSessionService{

    @Autowired
    private JdbcTemplate template;

    private final RowMapper<EstimateSession> estimateSessionRowMapper = (rs, rowNom) -> new EstimateSession(
                    rs.getString("uid"),
                    rs.getString("name"),
                    rs.getInt("capacity"));


    public EstimateSessionServiceJDBCImpl(JdbcTemplate template) {
        this.template = template;
    }

    public EstimateSessionServiceJDBCImpl() {}

    public Collection<EstimateSession> all() {
        return this.template.query("select * from estimate_session", this.estimateSessionRowMapper);
    }

    public EstimateSession byUID(String uid) throws ResourceNotFoundException {
        var estimateSessionList = this.template.query("select * from estimate_session where uid=?", this.estimateSessionRowMapper, uid);
        if (estimateSessionList.isEmpty() || estimateSessionList.size() > 1) {
            throw new ResourceNotFoundException();
        }
        return estimateSessionList.get(0);
    }

    @Override
    public String create(EstimateSession session) {
        return null;
    }

}
