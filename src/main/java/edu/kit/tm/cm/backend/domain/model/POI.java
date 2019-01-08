package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
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


    private int floorId;


    private int zLevel;


    private String buildingName;


    private String description;


    private ArrayList<double[]> door;


    private ArrayList<double[]> coordinates;

    //About the kind of poi it is. Office, Hallway, toilets. We could add Beacons to poi and make "Beacon" a name.

    private ArrayList<String> names;
}
