package edu.kit.tm.cm.backend.application.dtos.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Building")
public class BuildingResponse {
    @ApiModelProperty(position = 1, required = true)
    int id;

    @ApiModelProperty(position = 2, required = true)
    String name;

    @ApiModelProperty(position = 3, required = true)
    String description;
}
