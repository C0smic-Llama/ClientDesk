package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.DeliverableRequestDTO;
import com.project.ClientDesk.dto.DeliverableResponseDTO;
import com.project.ClientDesk.entity.Deliverable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliverableMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectService", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Deliverable toEntity(DeliverableRequestDTO requestDTO);


    @Mapping(target = "projectId", source = "projectService.project.id")
    @Mapping(target = "projectName", source = "projectService.project.projectName")
    @Mapping(target = "projectServiceId",source = "projectService.id")
    @Mapping(target = "serviceCatalogueId", source = "projectService.serviceCatalogue.id")
    @Mapping(target = "serviceName", source = "projectService.serviceCatalogue.serviceName")
    DeliverableResponseDTO toResponseDTO(Deliverable deliverable);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectService", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(DeliverableRequestDTO requestDTO,
                             @MappingTarget
                             Deliverable deliverable);
}
