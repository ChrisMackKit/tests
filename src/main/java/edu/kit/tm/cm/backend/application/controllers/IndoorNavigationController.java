package edu.kit.tm.cm.backend.application.controllers;

import edu.kit.tm.cm.backend.application.controllers.Api.IndoorNavigationApi;
import edu.kit.tm.cm.backend.application.dtos.BuildingMapper;
import edu.kit.tm.cm.backend.application.dtos.PositionMapper;
import edu.kit.tm.cm.backend.application.dtos.response.BuildingResponse;
import edu.kit.tm.cm.backend.application.dtos.response.PositionResponse;
import edu.kit.tm.cm.backend.application.services.IndoorNavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class IndoorNavigationController implements IndoorNavigationApi {
    private final IndoorNavigationService indoorNavigationService;
    private final BuildingMapper buildingMapper;
    private final PositionMapper positionMapper;

    @Autowired
    public IndoorNavigationController(IndoorNavigationService indoorNavigationService, BuildingMapper buildingMapper, PositionMapper positionMapper) {
        this.indoorNavigationService = indoorNavigationService;
        this.buildingMapper = buildingMapper;
        this.positionMapper = positionMapper;
    }
    @Override
    public List<BuildingResponse> getBuildings() {
        return buildingMapper.toResponseList(indoorNavigationService.getBuildings());
    }


    @Override
    public BuildingResponse getBuildingByID(Long id) {
        return  buildingMapper.toResponse(indoorNavigationService.getBuildingByID(id));
    }


    //BeaconID should be String, if it is the title
    @Override
    public BuildingResponse getBuildingByBeaconID(Long id) {
        return  buildingMapper.toResponse(indoorNavigationService.getBuildingByBeaconID(id));
    }

    @Override
    public PositionResponse getPositionByBeaconSignals(String beaconsSignals) {

        return positionMapper.toResponse(indoorNavigationService.getPositionByBeaconSignals(beaconsSignals));
    }
}
