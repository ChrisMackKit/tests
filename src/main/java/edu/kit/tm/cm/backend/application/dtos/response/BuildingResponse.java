package edu.kit.tm.cm.backend.application.dtos.response;

import edu.kit.tm.cm.backend.domain.model.Floor;
import edu.kit.tm.cm.backend.domain.model.POI;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@ApiModel("Building")
public class BuildingResponse {

    @ApiModelProperty(position = 1, required = true)
    int id;

    @ApiModelProperty(position = 2, required = true)
    String name;

    @ApiModelProperty(position = 3, required = true)
    int campusId;


    @ApiModelProperty(position = 4, required = true)
    String geometryType;


    @ApiModelProperty(position = 5, required = true)
    ArrayList<double[]> coordinates;


    @ApiModelProperty(position = 7, required = true)
    ArrayList<POI> pois;


    @ApiModelProperty(position = 6, required = true)
    ArrayList<Floor> floors;
}
