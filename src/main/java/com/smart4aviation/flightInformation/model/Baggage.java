package com.smart4aviation.flightInformation.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(name = "initial_value", sequenceName = "my_sequence", initialValue = 0)
@Table(name = "baggage")
public class Baggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "initial_value")
    @Column(name = "id")
    private Long id;

    @Column(name = "weight")
    private double weight;

    @Column(name = "weightUnit")
    private String weightUnit;

    @Column(name = "pieces")
    private Integer pieces;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Baggage baggage = (Baggage) o;
        return getId() != null && Objects.equals(getId(), baggage.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
