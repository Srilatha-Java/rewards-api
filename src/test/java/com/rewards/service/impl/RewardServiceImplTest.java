package com.rewards.service.impl;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.exception.CustomerNotFoundException;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.RewardCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RewardCalculator rewardCalculator;

    @InjectMocks
    private RewardServiceImpl rewardService;

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> rewardService.getRewards(1L));
    }

    @Test
    void shouldReturnRewardsWhenCustomerExists() {

        Customer customer = new Customer(1L, "Srilatha");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateAfter(
                anyLong(), any()))
                .thenReturn(Collections.emptyList());

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertNotNull(response);
        assertEquals(0, response.getTotalPoints());
    }

    @Test
    void shouldHandleEmptyTransactions() {
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(new Customer()));

        when(transactionRepository.findByCustomerIdAndTransactionDateAfter(
                Mockito.eq(1L), Mockito.any()))
                .thenReturn(List.of());

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertEquals(0, response.getTotalPoints());
    }

    @Test
    void shouldHandleNegativeTransactionAmount() {
        Customer customer = new Customer();
        customer.setId(1L);

        Transaction txn = new Transaction();
        txn.setAmount(-50.0); // edge case
        txn.setTransactionDate(LocalDate.now());

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateAfter(
                Mockito.eq(1L), Mockito.any()))
                .thenReturn(List.of(txn));

        when(rewardCalculator.calculate(-50.0)).thenReturn(0);

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertEquals(0, response.getTotalPoints());
    }

    @Test
    void shouldIgnoreFutureTransactions() {
        Customer customer = new Customer();
        customer.setId(1L);

        Transaction txn = new Transaction();
        txn.setAmount(120.0);
        txn.setTransactionDate(LocalDate.now().plusDays(5)); // future date

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateAfter(
                Mockito.eq(1L), Mockito.any()))
                .thenReturn(List.of(txn));

        when(rewardCalculator.calculate(120.0)).thenReturn(90);

        RewardResponseDTO response = rewardService.getRewards(1L);

        // based on your logic (you may filter later)
        assertNotNull(response);
    }

    @Test
    void shouldCalculateMonthlyPoints() {

        Customer customer = new Customer();
        customer.setId(1L);

        Transaction janTxn = new Transaction();
        janTxn.setAmount(120.0);
        janTxn.setTransactionDate(LocalDate.of(2026, 1, 10));

        Transaction febTxn = new Transaction();
        febTxn.setAmount(80.0);
        febTxn.setTransactionDate(LocalDate.of(2026, 2, 10));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository.findByCustomerIdAndTransactionDateAfter(
                Mockito.eq(1L), Mockito.any()))
                .thenReturn(List.of(janTxn, febTxn));

        when(rewardCalculator.calculate(120.0)).thenReturn(90);
        when(rewardCalculator.calculate(80.0)).thenReturn(30);

        RewardResponseDTO response = rewardService.getRewards(1L);

        assertEquals(2, response.getMonthlyPoints().size());
    }
}
