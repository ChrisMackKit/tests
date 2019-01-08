package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;


public class Position  {

    @Getter
    private double[] coordinates;
    /**
     * Or
    *private int x;
    *
    *private int y;
     */

}
