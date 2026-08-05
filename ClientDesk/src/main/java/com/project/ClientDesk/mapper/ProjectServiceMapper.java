package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.ProjectServiceRequestDTO;
import com.project.ClientDesk.dto.ProjectServiceResponseDTO;
import com.project.ClientDesk.entity.ProjectService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ProjectServiceMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "serviceCatalogue", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProjectService toEntity(ProjectServiceRequestDTO requestDTO);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.projectName")
    @Mapping(target = "serviceCatalogueId", source = "serviceCatalogue.id")
    @Mapping(target = "serviceName", source = "serviceCatalogue.serviceName")
    @Mapping(target = "lineTotal", source = "lineTotal")
    ProjectServiceResponseDTO toResponseDTO(ProjectService projectService);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "serviceCatalogue", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ProjectServiceRequestDTO requestDTO,
                             @MappingTarget
                             ProjectService projectService);



}
