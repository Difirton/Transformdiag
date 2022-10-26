package com.difirton.investigator.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "physical_chemical_oil_analyzes")
public class PhysicalChemicalOilAnalysis {

    @Id
    private Long id;

    @DateTimeFormat(pattern="dd.MM.yyyy")
    private LocalDate dateAnalysis;

    private Transformer transformer;

    private Double flashPoint;

    private Double acidNumber;

    private Integer cleanlinessClass;

    private Double moistureContent;

    private Double breakdownVoltage;

    private Double dielectricLossTangent;

    private String protocolName;
}
