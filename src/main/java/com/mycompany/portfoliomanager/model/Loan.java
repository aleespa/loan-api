package com.mycompany.portfoliomanager.model;

import java.time.LocalDate;

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

    private int principal;

    private LocalDate startDate;

    private LocalDate finalDate;

    private Interest interest;


}
