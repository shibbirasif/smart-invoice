package com.asif.smartinvoice.services;

import com.asif.smartinvoice.entities.InvoiceItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceItemService {

    InvoiceItem createInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem updateInvoiceItem(UUID id, InvoiceItem invoiceItem);

    Optional<InvoiceItem> getInvoiceItemById(UUID id);

    List<InvoiceItem> getInvoiceItemsByInvoiceId(UUID invoiceId);

    List<InvoiceItem> getAllInvoiceItems();

    void deleteInvoiceItem(UUID id);

    void deleteInvoiceItemsByInvoiceId(UUID invoiceId);

    BigDecimal calculateTotalByInvoiceId(UUID invoiceId);

    Long countByInvoiceId(UUID invoiceId);
}
