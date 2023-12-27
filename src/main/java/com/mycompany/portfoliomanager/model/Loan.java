package com.mycompany.portfoliomanager.model;

import java.time.LocalDate;

import com.mycompany.portfoliomanager.model.interest.Frequency;
import com.mycompany.portfoliomanager.model.interest.Interest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double principal;

    private LocalDate startDate;

    private Integer term;

    private Interest interest;

    private Frequency payment_frequency;

    private Boolean fixed_payments;

}
