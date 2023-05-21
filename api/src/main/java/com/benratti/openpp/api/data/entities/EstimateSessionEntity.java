package com.benratti.openpp.api.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EstimateSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uid;
    private String name;
    private int capacity;

    public EstimateSessionEntity(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public EstimateSessionEntity(String uid, String name, int capacity) {
        this.uid = uid;
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "EstimateSessionEntity{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
