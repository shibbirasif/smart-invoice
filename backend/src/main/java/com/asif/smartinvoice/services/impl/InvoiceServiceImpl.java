package com.asif.smartinvoice.services.impl;

import com.asif.smartinvoice.entities.Invoice;
import com.asif.smartinvoice.repositories.InvoiceRepository;
import com.asif.smartinvoice.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(Invoice invoice) {
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            invoice.setInvoiceNumber(generateInvoiceNumber());
        } else if (existsByInvoiceNumber(invoice.getInvoiceNumber())) {
            throw new RuntimeException("Invoice with number already exists");
        }
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(UUID id, Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getInvoiceNumber() != null && !invoice.getInvoiceNumber().equals(existingInvoice.getInvoiceNumber())) {
            if (existsByInvoiceNumber(invoice.getInvoiceNumber())) {
                throw new RuntimeException("Invoice with number already exists");
            }
            existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
        }

        if (invoice.getInvoiceDate() != null) {
            existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
        }
        if (invoice.getDueDate() != null) {
            existingInvoice.setDueDate(invoice.getDueDate());
        }
        if (invoice.getNotes() != null) {
            existingInvoice.setNotes(invoice.getNotes());
        }

        return invoiceRepository.save(existingInvoice);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> getInvoiceById(UUID id) {
        return invoiceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> getInvoiceByIdWithItems(UUID id) {
        return invoiceRepository.findByIdWithItems(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByUserId(UUID userId) {
        return invoiceRepository.findByUserIdOrderByInvoiceDateDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByUserIdWithItems(UUID userId) {
        return invoiceRepository.findByUserIdWithItems(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByUserIdAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findByUserIdAndInvoiceDateBetween(userId, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getOverdueInvoicesByUserId(UUID userId) {
        return invoiceRepository.findOverdueInvoicesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getAllOverdueInvoices() {
        return invoiceRepository.findAllOverdueInvoices();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public void deleteInvoice(UUID id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

    @Override
    public String generateInvoiceNumber() {
        String prefix = "INV";
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Random random = new Random();
        String randomPart = String.format("%04d", random.nextInt(10000));

        String invoiceNumber;
        do {
            invoiceNumber = prefix + "-" + datePart + "-" + randomPart;
            randomPart = String.format("%04d", random.nextInt(10000));
        } while (existsByInvoiceNumber(invoiceNumber));

        return invoiceNumber;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByInvoiceNumber(String invoiceNumber) {
        return invoiceRepository.existsByInvoiceNumber(invoiceNumber);
    }
}
