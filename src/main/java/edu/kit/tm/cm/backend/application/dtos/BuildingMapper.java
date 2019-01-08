package edu.kit.tm.cm.backend.application.dtos;

import edu.kit.tm.cm.backend.domain.model.Building;
import org.mapstruct.Mapper;
import edu.kit.tm.cm.backend.application.dtos.response.BuildingResponse;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    BuildingResponse toResponse(Building buildingDto);
    List<BuildingResponse> toResponseList(List<Building> buildingDtos);
}
