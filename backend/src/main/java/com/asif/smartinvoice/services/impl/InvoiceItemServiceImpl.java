package com.asif.smartinvoice.services.impl;

import com.asif.smartinvoice.entities.InvoiceItem;
import com.asif.smartinvoice.repositories.InvoiceItemRepository;
import com.asif.smartinvoice.services.InvoiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;

    @Override
    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem) {
        return invoiceItemRepository.save(invoiceItem);
    }

    @Override
    public InvoiceItem updateInvoiceItem(UUID id, InvoiceItem invoiceItem) {
        InvoiceItem existingItem = invoiceItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invoice item not found"));

        if (invoiceItem.getName() != null) {
            existingItem.setName(invoiceItem.getName());
        }
        if (invoiceItem.getQuantity() != null) {
            existingItem.setQuantity(invoiceItem.getQuantity());
        }
        if (invoiceItem.getUnitPrice() != null) {
            existingItem.setUnitPrice(invoiceItem.getUnitPrice());
        }

        return invoiceItemRepository.save(existingItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceItem> getInvoiceItemById(UUID id) {
        return invoiceItemRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(UUID invoiceId) {
        return invoiceItemRepository.findByInvoiceIdOrderByCreatedAtAsc(invoiceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemRepository.findAll();
    }

    @Override
    public void deleteInvoiceItem(UUID id) {
        if (!invoiceItemRepository.existsById(id)) {
            throw new RuntimeException("Invoice item not found");
        }
        invoiceItemRepository.deleteById(id);
    }

    @Override
    public void deleteInvoiceItemsByInvoiceId(UUID invoiceId) {
        invoiceItemRepository.deleteByInvoiceId(invoiceId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalByInvoiceId(UUID invoiceId) {
        BigDecimal total = invoiceItemRepository.calculateTotalByInvoiceId(invoiceId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByInvoiceId(UUID invoiceId) {
        return invoiceItemRepository.countByInvoiceId(invoiceId);
    }
}
