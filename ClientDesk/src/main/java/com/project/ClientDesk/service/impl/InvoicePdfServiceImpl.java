package com.project.ClientDesk.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.project.ClientDesk.entity.Invoice;
import com.project.ClientDesk.entity.ProjectService;
import com.project.ClientDesk.exception.ResourceNotFoundException;
import com.project.ClientDesk.repository.InvoiceRepository;
import com.project.ClientDesk.repository.ProjectServiceRepository;
import com.project.ClientDesk.service.InvoicePdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


@Slf4j
@Service
@RequiredArgsConstructor
public class InvoicePdfServiceImpl implements InvoicePdfService {

    private final InvoiceRepository invoiceRepository;
    private final ProjectServiceRepository projectServiceRepository;

    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(41, 128, 185));
    private static final Font HEADER_FONT = new Font(Font.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.HELVETICA, 11);
    private static final Font SMALL_FONT = new Font(Font.HELVETICA, 10);
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));


    @Override
    public byte[] generateInvoicePdf(Long invoiceId) {

        log.info("Generating invoice PDF for invoice with ID : {}",invoiceId);
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() ->
                new ResourceNotFoundException("Invoice does not exist with ID : " + invoiceId));

        List<ProjectService> services = projectServiceRepository.findByProject(invoice.getProject());


        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);

            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph company = new Paragraph("ZER04 STUDIOS", TITLE_FONT);
            company.setAlignment(Element.ALIGN_CENTER);
            document.add(company);

            Paragraph subtitle = new Paragraph("Digital Marketing Agency", HEADER_FONT);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            document.add(new Paragraph(" "));

            LineSeparator separator = new LineSeparator();
            separator.setLineColor(Color.black);

            document.add(separator);

            document.add(new Paragraph(" "));

            PdfPTable invoiceTable = new PdfPTable(2);
            invoiceTable.setWidthPercentage(100);
            invoiceTable.setWidths(new float[]{2, 2});

            PdfPCell leftCell = new PdfPCell();
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.addElement(new Phrase("Invoice No : " + invoice.getInvoiceNumber(), NORMAL_FONT));
            leftCell.addElement(new Phrase("Invoice Date : " + invoice.getInvoiceDate(), NORMAL_FONT));
            leftCell.addElement(new Phrase("Due Date : " + invoice.getDueDate(), NORMAL_FONT));

            PdfPCell rightCell = new PdfPCell();
            rightCell.setBorder(Rectangle.NO_BORDER);

            Paragraph status = new Paragraph("Status : " + invoice.getStatus(), HEADER_FONT);
            status.setAlignment(Element.ALIGN_RIGHT);
            rightCell.addElement(status);

            invoiceTable.addCell(leftCell);
            invoiceTable.addCell(rightCell);
            document.add(invoiceTable);

            document.add(new Paragraph(" "));

            Paragraph billTo = new Paragraph("Bill To : ", HEADER_FONT);
            document.add(billTo);

            document.add(new Paragraph(invoice.getProject().getClient().getCompanyName(), NORMAL_FONT));
            document.add(new Paragraph(invoice.getProject().getClient().getEmail(), NORMAL_FONT));
            document.add(new Paragraph(invoice.getProject().getClient().getContactNumber(), NORMAL_FONT));
            document.add(new Paragraph(" "));

            Paragraph project = new Paragraph("Project : ", HEADER_FONT);
            document.add(project);
            document.add(new Paragraph(invoice.getProject().getProjectName(), NORMAL_FONT));
            document.add(new Paragraph(" "));


            //services table
            PdfPTable serviceTable = new PdfPTable(3);
            serviceTable.setWidthPercentage(100);
            serviceTable.setSpacingBefore(10);
            serviceTable.setWidths(new float[]{5, 1.5f, 2});

            PdfPCell headerCell;
            headerCell = new PdfPCell(new Phrase("Service", HEADER_FONT));
            headerCell.setBackgroundColor(Color.white);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            serviceTable.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Quantity ", HEADER_FONT));
            headerCell.setBackgroundColor(Color.white);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            serviceTable.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Amount  ", HEADER_FONT));
            headerCell.setBackgroundColor(Color.white);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            serviceTable.addCell(headerCell);

            for (ProjectService service : services) {
                serviceTable.addCell(new Phrase(service.getServiceCatalogue().getServiceName(), NORMAL_FONT));
                PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(service.getQuantity()), NORMAL_FONT));
                quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                serviceTable.addCell(quantityCell);
                PdfPCell amountCell = new PdfPCell(new Phrase(CURRENCY.format(service.getLineTotal()), NORMAL_FONT));
                amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                serviceTable.addCell(amountCell);
            }
            document.add(serviceTable);
            document.add(new Paragraph(" "));

            //final amount table
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(40);
            totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalTable.setSpacingBefore(10);
            totalTable.setWidths(new float[]{2, 2});

            totalTable.addCell(createSummaryCell("Taxable Amount"));
            totalTable.addCell(createAmountCell(CURRENCY.format(invoice.getTaxableAmount())));

            totalTable.addCell(createSummaryCell("GST(" + invoice.getGstPercentage().stripTrailingZeros().toPlainString() + "%)"));
            totalTable.addCell(createAmountCell(CURRENCY.format(invoice.getGstAmount())));

            totalTable.addCell(createSummaryCell("Discount"));
            totalTable.addCell(createAmountCell(CURRENCY.format(invoice.getDiscount())));

            totalTable.addCell(createSummaryCell("Grand Total"));
            totalTable.addCell(createAmountCell(CURRENCY.format(invoice.getGrandTotal())));

            document.add(totalTable);
            document.add(separator);
            document.add(new Paragraph(" "));

            Paragraph thankYou = new Paragraph("Thank You for your business", HEADER_FONT);
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

            Paragraph footer = new Paragraph("Generated by ClientDesk", SMALL_FONT);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            document.close();

            log.info("Invoice PDF generated successfully for Invoice : {}",invoice.getInvoiceNumber());
            return outputStream.toByteArray();


        } catch (Exception ex) {
            log.error("Failed to generate PDF for invoice ID : {}",invoiceId,ex);
            throw new RuntimeException("Failer to generate the invoice PDF", ex);
        }

    }

    private PdfPCell createSummaryCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, HEADER_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell createAmountCell(String amount) {
        PdfPCell cell = new PdfPCell(new Phrase(amount, NORMAL_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }
}
