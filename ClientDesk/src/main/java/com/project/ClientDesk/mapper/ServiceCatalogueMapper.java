package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.ServiceCatalogueRequestDTO;
import com.project.ClientDesk.dto.ServiceCatalogueResponseDTO;
import com.project.ClientDesk.entity.ServiceCatalogue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceCatalogueMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "active",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    ServiceCatalogue toEntity(ServiceCatalogueRequestDTO requestDTO);

    ServiceCatalogueResponseDTO toResponseDTO(ServiceCatalogue serviceCatalogue);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "active",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    void updateEntityFromDTO(ServiceCatalogueRequestDTO requestDTO,
                             @MappingTarget
                             ServiceCatalogue serviceCatalogue);
}
