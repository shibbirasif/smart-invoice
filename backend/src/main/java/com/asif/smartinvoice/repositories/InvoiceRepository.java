package com.asif.smartinvoice.repositories;

import com.asif.smartinvoice.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    List<Invoice> findByUserId(UUID userId);

    List<Invoice> findByUserIdOrderByInvoiceDateDesc(UUID userId);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate);

    List<Invoice> findByUserIdAndInvoiceDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT i FROM Invoice i WHERE i.userId = :userId AND i.dueDate < CURRENT_DATE")
    List<Invoice> findOverdueInvoicesByUserId(@Param("userId") UUID userId);

    @Query("SELECT i FROM Invoice i WHERE i.dueDate < CURRENT_DATE")
    List<Invoice> findAllOverdueInvoices();

    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.items WHERE i.id = :id")
    Optional<Invoice> findByIdWithItems(@Param("id") UUID id);

    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.items WHERE i.userId = :userId")
    List<Invoice> findByUserIdWithItems(@Param("userId") UUID userId);

    boolean existsByInvoiceNumber(String invoiceNumber);
}
