package com.mycompany.portfoliomanager.model.interest;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Interest {

    private double rate;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;
}
