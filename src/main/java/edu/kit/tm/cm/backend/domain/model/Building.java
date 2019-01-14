package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.ArrayList;


@Entity
@AllArgsConstructor
@Builder
public class Building  extends EntityBase {


    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int campusId;
    @Getter
    @Setter
    private ArrayList<Floor> floors;
    @Getter
    @Setter
    private ArrayList<POI> pois;
    @Getter
    @Setter
    private ArrayList<double[]> coordinates;
    @Getter
    @Setter
    private double[] systemZero;
    private String geometryType;

    public Building() {

    }

    public String getGeometryType() {
        return this.geometryType;
    }

    public void setGeometryType(String type) {
        this.geometryType = type;
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

    public int getCampusId() {
        return campusId;
    }

    public void setCampusId(int campusId) {
        this.campusId = campusId;
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    public ArrayList<POI> getPois() {
        return pois;
    }

    public void setPois(ArrayList<POI> pois) {
        this.pois = pois;
    }

    public ArrayList<double[]> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<double[]> coordinates) {
        this.coordinates = coordinates;
    }

    //public void setCoordinates(ArrayList<double[]> coordinates) {
    //    this.coordinates = newList(coordinates);
    //}

    public double[] getSystemZero() {
        return systemZero;
    }

    public void setSystemZero(double[] systemZero) {
        this.systemZero = systemZero;
    }


    //Find point 0,0 for the Indoor Coordinate System
    public static double[] findZero(ArrayList<double[]> coordinates) {
        double[] systemZero =  {0.0, 0.0};
        systemZero[0] = coordinates.get(0)[0];
        systemZero[1] = coordinates.get(0)[1];
        for(int i = 1; i < coordinates.size(); i++){
            if (coordinates.get(i)[0] < systemZero[0]) {
                systemZero[0] = coordinates.get(i)[0];
            }
            if (coordinates.get(i)[1] > systemZero[1]) {
                systemZero[1] = (coordinates.get(i)[1]);
            }
        }
        return systemZero;
    }
    // convert from GeoJson coordinates into our coordinates in Meter
    public static double[] intoMeter(double[] coordinates, double[] zero) {
        //x coordinate
        double R = 6378.137; // Radius of earth in KM
        double dLat1 = zero[1] * Math.PI / 180 - zero[1] * Math.PI / 180;
        double dLon1 = coordinates[0] * Math.PI / 180 - zero[0] * Math.PI / 180;
        double a = Math.sin(dLat1/2) * Math.sin(dLat1/2) +
                Math.cos(zero[1] * Math.PI / 180) * Math.cos(zero[1] * Math.PI / 180) *
                        Math.sin(dLon1/2) * Math.sin(dLon1/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        double xValue = d * 1000; // meters

        //y coordinate
        double dLat2 = coordinates[1] * Math.PI / 180 - zero[1] * Math.PI / 180;
        double dLon2 = zero[0] * Math.PI / 180 - zero[0] * Math.PI / 180;
        double e = Math.sin(dLat2/2) * Math.sin(dLat2/2) +
                Math.cos(zero[1] * Math.PI / 180) * Math.cos(coordinates[1] * Math.PI / 180) *
                        Math.sin(dLon2/2) * Math.sin(dLon2/2);
        double f = 2 * Math.atan2(Math.sqrt(e), Math.sqrt(1-e));
        double g = R * f;
        double yValue = g * 1000; // meters

        double[] newXyCoordinates = {xValue, yValue};
        return newXyCoordinates;
    }

    //uses function intoMeter for whole list. Returns the Coordinates in new format (Meter) as ArrayList<double[]>
    public static  ArrayList<double[]> newList(ArrayList<double[]> coordinates) {
        double[] zero = findZero(coordinates);
        ArrayList<double[]> newCoords = new ArrayList<double[]>();
        for (int j = 0; j < coordinates.size(); j++) {
            newCoords.add(j, intoMeter(coordinates.get(j), zero));
        }
        return newCoords;
    }

}
