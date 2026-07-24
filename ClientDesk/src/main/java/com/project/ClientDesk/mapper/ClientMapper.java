package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.ClientRequestDTO;
import com.project.ClientDesk.dto.ClientResponseDTO;
import com.project.ClientDesk.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)

public interface ClientMapper {

    Client toEntity(ClientRequestDTO requestDTO);

    ClientResponseDTO toResponseDTO(Client client);

    void updateEntityFromDTO(ClientRequestDTO requestDTO,
                             @MappingTarget Client client);

}
