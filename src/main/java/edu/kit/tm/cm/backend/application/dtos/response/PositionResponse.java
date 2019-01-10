package edu.kit.tm.cm.backend.application.dtos.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Position")
public class PositionResponse {

    @ApiModelProperty(position = 1, required = true)
    private double[] coordinates;
}


