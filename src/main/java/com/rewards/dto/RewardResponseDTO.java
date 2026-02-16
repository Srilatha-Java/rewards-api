package com.rewards.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardResponseDTO {

    private Long customerId;
    private Map<String, Integer> monthlyPoints;
    private Integer totalPoints;
}