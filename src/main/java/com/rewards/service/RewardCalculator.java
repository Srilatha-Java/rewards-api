package com.rewards.service;

import org.springframework.stereotype.Component;

@Component
public class RewardCalculator {

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
