package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.InvoiceRequestDTO;
import com.project.ClientDesk.dto.InvoiceResponseDTO;
import com.project.ClientDesk.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "id",ignore=true)
    @Mapping(target = "invoiceNumber",ignore=true)
    @Mapping(target = "project",ignore=true)
    @Mapping(target = "invoiceDate",ignore=true)
    @Mapping(target = "taxableAmount",ignore=true)
    @Mapping(target = "gstAmount",ignore=true)
    @Mapping(target = "grandTotal",ignore=true)
    @Mapping(target = "status",ignore=true)
    @Mapping(target = "createdAt",ignore=true)
    @Mapping(target = "updatedAt",ignore=true)
    Invoice toEntity(InvoiceRequestDTO requestDTO);

    @Mapping(target = "clientName", source = "project.client.companyName")
    @Mapping(target = "projectName", source = "project.projectName")
    InvoiceResponseDTO toResponseDTO(Invoice invoice);

    @Mapping(target = "id",ignore=true)
    @Mapping(target = "invoiceNumber",ignore=true)
    @Mapping(target = "project",ignore=true)
    @Mapping(target = "invoiceDate",ignore=true)
    @Mapping(target = "taxableAmount",ignore=true)
    @Mapping(target = "gstAmount",ignore=true)
    @Mapping(target = "grandTotal",ignore=true)
    @Mapping(target = "status",ignore=true)
    @Mapping(target = "createdAt",ignore=true)
    @Mapping(target = "updatedAt",ignore=true)
    void updateEntityFromDTO(InvoiceRequestDTO requestDTO,
                             @MappingTarget
                             Invoice invoice);



}
