package com.rewards.service;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.exception.CustomerNotFoundException;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for reward calculations.
 */
@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final RewardCalculator rewardCalculator;

    /**
     * Returns reward points for a customer for last 3 months.
     *
     * @param customerId customer id
     * @return reward response DTO
     */
    public RewardResponseDTO getRewards(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found: " + customerId));

        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        List<Transaction> transactions =
                transactionRepository.findByCustomerIdAndTransactionDateAfter(
                        customerId, threeMonthsAgo);

        Map<String, Integer> monthlyPoints =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                t -> t.getTransactionDate().getMonth().toString(),
                                Collectors.summingInt(
                                        t -> rewardCalculator.calculate(t.getAmount())
                                )
                        ));

        int totalPoints = monthlyPoints.values()
                .stream()
                .reduce(0, Integer::sum);

        return new RewardResponseDTO(customerId, monthlyPoints, totalPoints);
    }

    @Override
    public List<RewardResponseDTO> getAllRewards() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(customer -> getRewards(customer.getId()))
                .toList();
    }
}
