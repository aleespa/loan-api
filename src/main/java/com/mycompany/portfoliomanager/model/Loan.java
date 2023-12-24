package com.mycompany.portfoliomanager.model;

import java.time.LocalDate;
import java.util.Optional;

import com.mycompany.portfoliomanager.model.interest.Frequency;
import com.mycompany.portfoliomanager.model.interest.Interest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double principal;

    private LocalDate startDate;

    private Integer term;

    private Interest interest;

    private Frequency frequency;

    private Boolean fixed_payments;


}
