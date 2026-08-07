package com.project.ClientDesk.mapper;


import com.project.ClientDesk.dto.PaymentRequestDTO;
import com.project.ClientDesk.dto.PaymentResponseDTO;
import com.project.ClientDesk.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "invoice",ignore = true)
    @Mapping(target = "receiptNumber",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    Payment toEntity(PaymentRequestDTO requestDTO);

    @Mapping(target = "invoiceId", source = "invoice.id")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    @Mapping(target = "status", source = "invoice.status")
    @Mapping(target = "clientName", source = "invoice.project.client.companyName")
    @Mapping(target = "projectName", source = "invoice.project.projectName")
    @Mapping(target = "totalPaid", ignore = true)
    @Mapping(target = "pendingAmount", ignore = true)
    PaymentResponseDTO toResponseDTO(Payment payment);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "invoice",ignore = true)
    @Mapping(target = "receiptNumber",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    void updateEntityFromDTO(PaymentRequestDTO requestDTO,
                             @MappingTarget
                             Payment payment);
}
