package edu.kit.tm.cm.backend.application.dtos;


import edu.kit.tm.cm.backend.application.dtos.response.PositionResponse;
import edu.kit.tm.cm.backend.domain.model.Position;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    PositionResponse toResponse(Position position);
}
