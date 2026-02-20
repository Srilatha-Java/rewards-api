package com.rewards.service;

import org.springframework.stereotype.Component;

/**
 * Utility class responsible for calculating reward points
 * based on transaction amount.
 */
@Component
public class RewardCalculator {

    /**
     * Calculates reward points based on business rules:
     * - 2 points for every dollar spent above 100
     * - 1 point for every dollar spent between 50 and 100
     *
     * @param amount transaction amount
     * @return calculated reward points
     */
    public int calculate(double amount) {

        if (amount <= 50) {
            return 0;
        }

        int points = 0;

        if (amount > 100) {
            points += (int) ((amount - 100) * 2);
            amount = 100;
        }

        points += (int) (amount - 50);

        return points;
    }
}
