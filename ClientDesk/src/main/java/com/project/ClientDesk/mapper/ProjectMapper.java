package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.ProjectRequestDTO;
import com.project.ClientDesk.dto.ProjectResponseDTO;
import com.project.ClientDesk.dto.UserEssentialDTO;
import com.project.ClientDesk.entity.Project;
import com.project.ClientDesk.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Project toEntity(ProjectRequestDTO requestDTO);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.companyName")
    @Mapping(target = "assignedUsers", source = "assignedUsers")
    ProjectResponseDTO toResponseDTO(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(ProjectRequestDTO dto,
                             @MappingTarget
                             Project project);

    UserEssentialDTO toUserEssentialDTO(User user);

    Set<UserEssentialDTO> toUserEssentialDTOSet(Set<User> users);


}
