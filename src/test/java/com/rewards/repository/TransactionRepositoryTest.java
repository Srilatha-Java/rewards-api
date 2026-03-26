package com.rewards.repository;

import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFetchTransactionsAfterDate() {

        Customer customer = new Customer();
        customer.setName("Test User");

        customer = customerRepository.save(customer); // auto ID

        Transaction t1 = new Transaction();
        t1.setCustomer(customer);
        t1.setAmount(120.0);
        t1.setTransactionDate(LocalDate.now().minusDays(10));

        transactionRepository.save(t1);

        List<Transaction> result =
                transactionRepository.findByCustomerIdAndTransactionDateAfter(
                        customer.getId(),
                        LocalDate.now().minusMonths(1)
                );

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenNoTransactionsFound() {

        List<Transaction> result =
                transactionRepository.findByCustomerIdAndTransactionDateAfter(
                        999L, LocalDate.now());

        assertTrue(result.isEmpty());
    }

}
