package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
public class Floor  extends EntityBase {

    @Getter
    private Long id;

    private String name;


    private Long buildingId;


    private int campusId;


    private int z;


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public int getCampusId() {
        return campusId;
    }

    public void setCampusId(int campusId) {
        this.campusId = campusId;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}