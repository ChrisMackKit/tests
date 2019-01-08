package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Building  extends EntityBase {

    @Getter
    private Long id;


    private String name;


    private int campusId;

    private ArrayList<Floor> floors;


    private ArrayList<double[]> coordinates;

    private double[] systemZero;
/*
    //Find point 0,0 for the Indoor Coordinate System
    public Coordinates findZero(ArrayList<Coordinates> coordinates) {
        systemZero = coordinates.get(0);
        for(int i = 1; i < coordinates.size(); i++){
            if (coordinates.get(i).getXy()[0] < systemZero.getXy()[0]) {
                systemZero.setX(coordinates.get(i).getXy()[0]);
            }
            if (coordinates.get(i).getXy()[1] < systemZero.getXy()[1]) {
                systemZero.setY(coordinates.get(i).getXy()[1]);
            }
        }
        return systemZero;
    }
    // convert from GeoJson coordinates into our coordinates in Meter
    public Coordinates intoMeter(Coordinates coordinates, Coordinates zero) {
        //x coordinate
        double R = 6378.137; // Radius of earth in KM
        double dLat1 = zero.getXy()[1] * Math.PI / 180 - zero.getXy()[1] * Math.PI / 180;
        double dLon1 = coordinates.getXy()[0] * Math.PI / 180 - zero.getXy()[0] * Math.PI / 180;
        double a = Math.sin(dLat1/2) * Math.sin(dLat1/2) +
                Math.cos(zero.getXy()[1] * Math.PI / 180) * Math.cos(zero.getXy()[1] * Math.PI / 180) *
                        Math.sin(dLon1/2) * Math.sin(dLon1/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        double xValue = d * 1000; // meters

        //y coordinate
        double dLat2 = coordinates.getXy()[1] * Math.PI / 180 - zero.getXy()[1] * Math.PI / 180;
        double dLon2 = zero.getXy()[0] * Math.PI / 180 - zero.getXy()[0] * Math.PI / 180;
        double e = Math.sin(dLat2/2) * Math.sin(dLat2/2) +
                Math.cos(zero.getXy()[1] * Math.PI / 180) * Math.cos(coordinates.getXy()[1] * Math.PI / 180) *
                        Math.sin(dLon2/2) * Math.sin(dLon2/2);
        double f = 2 * Math.atan2(Math.sqrt(e), Math.sqrt(1-e));
        double g = R * f;
        double yValue = g * 1000; // meters

        double[] newXyCoordinates = {xValue, yValue};
        Coordinates newCoordinates = new Coordinates(newXyCoordinates);
        return newCoordinates;
    }

    //uses function intoMeter for whole list
    public ArrayList<Coordinates> newList(ArrayList<Coordinates> coordinates) {
        Coordinates zero = findZero(coordinates);
        for (int j = 0; j < coordinates.size(); j++) {
            coordinates.set(j, intoMeter(coordinates.get(j), zero));
        }
        return coordinates;
    }*/

}
