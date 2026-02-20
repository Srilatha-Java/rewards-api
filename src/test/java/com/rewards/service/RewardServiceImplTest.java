package com.rewards.service;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.entity.Customer;
import com.rewards.exception.CustomerNotFoundException;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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
}
