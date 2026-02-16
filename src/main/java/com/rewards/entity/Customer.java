package com.rewards.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a customer.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}