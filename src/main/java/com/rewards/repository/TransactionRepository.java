package com.rewards.repository;

import com.rewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Transaction entity.
 * Provides database operations for transactions.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Fetches transactions of a customer after the given date.
     *
     * @param customerId the customer ID
     * @param date the start date filter
     * @return list of transactions
     */
    List<Transaction> findByCustomerIdAndTransactionDateAfter(
            Long customerId, LocalDate date);
}
