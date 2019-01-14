package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.Getter;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class POI  extends EntityBase {


    @Getter
    private Long id;


    private String title;

    //Not sure what the difference between title and identifier is but the GeoJson from the examples had both

    private String identifier;

    //same with poiId and Id

    private int poiId;

    private int campusId;



    private int floorId;


    private int zLevel;


    private String buildingName;

    private String floorName;


    private String description;


    private ArrayList<double[]> door;


    private ArrayList<double[]> coordinates;

    //About the kind of poi it is. Office, Hallway, toilets. We could add Beacons to poi and make "Beacon" a name.

    private ArrayList<String> names;


    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public int getzLevel() {
        return zLevel;
    }

    public void setzLevel(int zLevel) {
        this.zLevel = zLevel;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<double[]> getDoor() {
        return door;
    }

    public void setDoor(ArrayList<double[]> door) {
        this.door = door;
    }

    public ArrayList<double[]> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<double[]> coordinates) {
        this.coordinates = coordinates;
    }
    //public void setCoordinates(ArrayList<double[]> coordinates, ArrayList<double[]> buildingCoordinates) {
    //        this.coordinates = newList(coordinates, buildingCoordinates);
    //    }
    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public int getCampusId() {
        return campusId;
    }

    public void setCampusId(int campusId) {
        this.campusId = campusId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    //uses function intoMeter for whole list. Returns the Coordinates in new format (Meter) as ArrayList<double[]>
    public static  ArrayList<double[]> newList(ArrayList<double[]> coordinates, ArrayList<double[]> coordinatesBuilding) {
        double[] zero = Building.findZero(coordinatesBuilding);
        ArrayList<double[]> newCoords = new ArrayList<double[]>();
        for (int j = 0; j < coordinates.size(); j++) {
            newCoords.add(j, Building.intoMeter(coordinates.get(j), zero));
        }
        return newCoords;
    }
}
