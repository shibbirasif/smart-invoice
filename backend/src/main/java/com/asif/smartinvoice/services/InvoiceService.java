package com.asif.smartinvoice.services;

import com.asif.smartinvoice.entities.Invoice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceService {

    Invoice createInvoice(Invoice invoice);

    Invoice updateInvoice(UUID id, Invoice invoice);

    Optional<Invoice> getInvoiceById(UUID id);

    Optional<Invoice> getInvoiceByIdWithItems(UUID id);

    Optional<Invoice> getInvoiceByNumber(String invoiceNumber);

    List<Invoice> getInvoicesByUserId(UUID userId);

    List<Invoice> getInvoicesByUserIdWithItems(UUID userId);

    List<Invoice> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate);

    List<Invoice> getInvoicesByUserIdAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate);

    List<Invoice> getOverdueInvoicesByUserId(UUID userId);

    List<Invoice> getAllOverdueInvoices();

    List<Invoice> getAllInvoices();

    void deleteInvoice(UUID id);

    String generateInvoiceNumber();

    boolean existsByInvoiceNumber(String invoiceNumber);
}
