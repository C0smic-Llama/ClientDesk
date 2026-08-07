package com.project.ClientDesk.service;

public interface InvoicePdfService {

    byte[] generateInvoicePdf(Long invoiceId);
}
