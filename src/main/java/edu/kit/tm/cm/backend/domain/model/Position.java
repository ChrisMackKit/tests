package edu.kit.tm.cm.backend.domain.model;

import lombok.Getter;


public class Position  {

    @Getter
    private double[] coordinates;

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Or
    *private int x;
    *
    *private int y;
     */

}
