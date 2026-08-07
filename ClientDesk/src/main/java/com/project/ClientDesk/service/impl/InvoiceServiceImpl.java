package com.project.ClientDesk.service.impl;

import com.project.ClientDesk.dto.InvoiceRequestDTO;
import com.project.ClientDesk.dto.InvoiceResponseDTO;
import com.project.ClientDesk.entity.Invoice;
import com.project.ClientDesk.entity.Project;
import com.project.ClientDesk.entity.ProjectService;
import com.project.ClientDesk.exception.DuplicateResourceException;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.mapper.InvoiceMapper;
import com.project.ClientDesk.repository.InvoiceRepository;
import com.project.ClientDesk.repository.ProjectRepository;
import com.project.ClientDesk.repository.ProjectServiceRepository;
import com.project.ClientDesk.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final ProjectRepository projectRepository;
    private final ProjectServiceRepository projectServiceRepository;
    private final InvoiceMapper invoiceMapper;


    private String generateInvoiceNumber(){
        long count = invoiceRepository.count()+1;
        return String.format("INV-%d-%05d", Year.now().getValue(),count);
    }

    private BigDecimal calculateTotal(Project project){
        List<ProjectService> projectServiceList = projectServiceRepository.findByProject(project);
        return projectServiceList.stream()
                .map(ProjectService::getLineTotal)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private BigDecimal calculateGSTAmount(BigDecimal grandTotal, BigDecimal gstPercentage){
        if(grandTotal==null){
            return BigDecimal.ZERO;
        }

        return grandTotal.multiply(gstPercentage)
                .divide(BigDecimal.valueOf(100).add(gstPercentage),2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTaxableAmount(BigDecimal grandTotal, BigDecimal gstAmount){
        return grandTotal.subtract(gstAmount);
    }
    private void calculateInvoiceAmounts(Invoice invoice, Project project){
        BigDecimal projectTotal = calculateTotal(project);

        BigDecimal grandTotal = projectTotal.subtract(invoice.getDiscount());
        invoice.setGrandTotal(grandTotal);

        BigDecimal gstAmount = calculateGSTAmount(grandTotal,invoice.getGstPercentage());
        invoice.setGstAmount(gstAmount);

        invoice.setTaxableAmount(grandTotal.subtract(gstAmount));
    }

    private Project getProject(Long projectId){
        return projectRepository.findById(projectId).orElseThrow(()->
                new ResourceNotFoundException("Project not found with ID : "+projectId));
    }

    private void validateInvoice(Project project){
        if(invoiceRepository.findByProject(project).isPresent()){
            throw new DuplicateResourceException("Invoice already exists for this project");
        }
    }

    @Override
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO requestDTO) {
        Project project  = getProject(requestDTO.getProjectId());
        validateInvoice(project);
        Invoice invoice = invoiceMapper.toEntity(requestDTO);
        invoice.setProject(project);
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setStatus(Invoice.InvoiceStatus.SENT);
        if(requestDTO.getDiscount()==null){
            requestDTO.setDiscount(BigDecimal.ZERO);
        }
        invoice.setDiscount(requestDTO.getDiscount());
        calculateInvoiceAmounts(invoice,project);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(savedInvoice);
    }

    @Override

    public InvoiceResponseDTO updateInvoice(Long id, InvoiceRequestDTO requestDTO) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Invoice not found with ID : "+id));
        Project project  = getProject(requestDTO.getProjectId());

        if(!invoice.getProject().getId().equals(project.getId())){
            if(invoiceRepository.findByProject(project).isPresent()){
                throw new DuplicateResourceException("Invoice already exists for this project");
            }
        }
        invoiceMapper.updateEntityFromDTO(requestDTO,invoice);
        invoice.setProject(project);
        if(requestDTO.getDiscount()==null){
            requestDTO.setDiscount(BigDecimal.ZERO);
        }else{
            invoice.setDiscount(requestDTO.getDiscount());
        }
        calculateInvoiceAmounts(invoice,project);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(updatedInvoice);

    }

    @Override
    public void deleteInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Invoice not found with ID : "+id));
        invoiceRepository.delete(invoice);

    }

    @Override
    public InvoiceResponseDTO getInvoiceById(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Invoice not found with ID : "+id ));
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public InvoiceResponseDTO getInvoiceByNumber(String invoiceNumber) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber).orElseThrow(()->
                new ResourceNotFoundException("Invoice not found with invoice number : "+invoiceNumber));
        return invoiceMapper.toResponseDTO(invoice);

    }

    @Override
    public InvoiceResponseDTO getInvoiceByProject(Long projectId) {
        Project project = getProject(projectId);
        Invoice invoice = invoiceRepository.findByProject(project).orElseThrow(()->
                new ResourceNotFoundException("Invoice not found for the projectId : "+projectId));
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public Page<InvoiceResponseDTO> getInvoicesByClient(Long clientId, Pageable pageable) {
        return invoiceRepository.findByClientId(clientId, pageable)
                .map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Page<InvoiceResponseDTO> getInvoicesByStatus(Invoice.InvoiceStatus status, Pageable pageable) {
        return invoiceRepository.getByStatus(status,pageable)
                .map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Page<InvoiceResponseDTO> getInvoicesByInvoiceDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return invoiceRepository.findByInvoiceDateBetween(startDate,endDate,pageable)
                .map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Page<InvoiceResponseDTO> getInvoicesByDueDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return invoiceRepository.findByDueDateBetween(startDate,endDate,pageable)
                .map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Page<InvoiceResponseDTO> getOverdueInvoices(Pageable pageable) {
        return invoiceRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), Invoice.InvoiceStatus.PAID,pageable)
                .map(invoiceMapper::toResponseDTO);
    }
}
