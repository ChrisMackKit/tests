package edu.kit.tm.cm.backend.domain.model;

import edu.kit.tm.cm.msutils.ddd.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Floor  extends EntityBase {

    @Getter
    private Long id;

    private String name;


    private int buildingId;


    private int campusId;


    private int z;
}