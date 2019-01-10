package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
public class Beacon extends EntityBase {

    @Getter
    private Long id;

    private int poiId;

    private int floorId;

    private Long buildingId;

    private String name;

    private double[] position = new double[2];

    private double rssi;

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double getRssi() {
        return rssi;
    }

    public void setRssi(double rssi) {
        this.rssi = rssi;
    }
}
