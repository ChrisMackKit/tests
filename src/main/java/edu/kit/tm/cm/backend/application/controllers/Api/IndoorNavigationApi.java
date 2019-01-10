package edu.kit.tm.cm.backend.application.controllers.Api;


import edu.kit.tm.cm.backend.application.dtos.response.BuildingResponse;
import edu.kit.tm.cm.backend.application.dtos.response.PositionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/IndoorNavigation")
@Api(tags = "IndoorNavigationsAPI", description = "Indoor Navigation Api")
public interface IndoorNavigationApi {
    @GetMapping("/Buildings")
    @ApiOperation(value = "Finds all Buildings")
    List<BuildingResponse> getBuildings();

    @GetMapping("/Buildings/{id}")
    @ApiOperation(value = "Finds a specific Building with ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Building not found")
    })
    BuildingResponse getBuildingByID(@PathVariable Long id);

    @GetMapping("/Buildings/Beacon/{id}")
    @ApiOperation(value = "Finds a specific Building with Beacon ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Building not found")
    })
    BuildingResponse getBuildingByBeaconID(@PathVariable Long id);


    @GetMapping("/determinePosition")
    @ApiOperation(value = "determine the position of the user")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Position can't be determined")
    })
    PositionResponse getPositionByBeaconSignals(@RequestBody String beaconsSignals);
}
