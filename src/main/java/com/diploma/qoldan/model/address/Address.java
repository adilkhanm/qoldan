package com.diploma.qoldan.model.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;
    private String address;

    private String building;
    private String apartment;
    private String entrance;
    private String details;

    @Override
    public String toString() {
        return city + ", " + address + ", building:" + building + ", apartment: " + apartment + ", entrance: " + entrance;
    }
}
