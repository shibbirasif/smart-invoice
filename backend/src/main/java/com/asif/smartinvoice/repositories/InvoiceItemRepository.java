package com.asif.smartinvoice.repositories;

import com.asif.smartinvoice.entities.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, UUID> {

    List<InvoiceItem> findByInvoiceId(UUID invoiceId);

    List<InvoiceItem> findByInvoiceIdOrderByCreatedAtAsc(UUID invoiceId);

    @Query("SELECT SUM(ii.totalPrice) FROM InvoiceItem ii WHERE ii.invoiceId = :invoiceId")
    BigDecimal calculateTotalByInvoiceId(@Param("invoiceId") UUID invoiceId);

    @Query("SELECT COUNT(ii) FROM InvoiceItem ii WHERE ii.invoiceId = :invoiceId")
    Long countByInvoiceId(@Param("invoiceId") UUID invoiceId);

    void deleteByInvoiceId(UUID invoiceId);
}
