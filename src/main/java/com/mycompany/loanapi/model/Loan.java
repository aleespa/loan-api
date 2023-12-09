package com.mycompany.loanapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    public Loan() {
    }

    // Constructor with parameters
    public Loan(Long id, int amount) {
        this.id = id;
        this.amount = amount;
    }



}
