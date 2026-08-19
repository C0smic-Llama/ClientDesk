package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.UserRequestDTO;
import com.project.ClientDesk.dto.UserResponseDTO;
import com.project.ClientDesk.dto.UserUpdateRequestDTO;
import com.project.ClientDesk.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO toResponseDTO(User user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(UserUpdateRequestDTO userRequestDTO,
                             @MappingTarget
                             User user);
}
