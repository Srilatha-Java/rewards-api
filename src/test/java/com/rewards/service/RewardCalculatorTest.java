package com.rewards.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for RewardCalculator.
 */
class RewardCalculatorTest {

    private final RewardCalculator calculator = new RewardCalculator();

    @Test
    void test120Amount() {

        assertEquals(90, calculator.calculate(120));
    }

    @Test
    void test75Amount() {

        assertEquals(25, calculator.calculate(75));
    }

    @Test
    void testBelow50() {

        assertEquals(0, calculator.calculate(40));
    }
}
