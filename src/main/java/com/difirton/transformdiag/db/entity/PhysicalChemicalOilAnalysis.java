package com.difirton.transformdiag.db.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Validated
@Entity
@Table(name = "physical_chemical_oil_analysis")
public class PhysicalChemicalOilAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @PastOrPresent
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @Column(name = "date_analysis")
    private LocalDate dateAnalysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transformer")
    private Transformer transformer;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 2",
            integer = 4, fraction = 2)
    @Column(name = "flash_point")
    private Double flashPoint;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 5, fraction = 5)
    @Column(name = "acid_number")
    private Double acidNumber;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2",
            integer = 2, fraction = 0)
    @Column(name = "cleanliness_class")
    private Integer cleanlinessClass;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 5, fraction = 5)
    @Column(name = "moisture_content")
    private Double moistureContent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 3, fraction = 2)
    @Column(name = "breakdown_voltage")
    private Double breakdownVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 5",
            integer = 2, fraction = 5)
    @Column(name = "dielectric_loss_tangent")
    private Double dielectricLossTangent;

    @Size(max = 40, message = "Should be less than 40 characters")
    @Column(name = "protocol_name", length = 40)
    private String protocolName;
}
