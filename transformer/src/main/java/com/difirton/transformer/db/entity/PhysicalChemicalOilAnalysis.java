package com.difirton.transformer.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"transformer"})
@Builder
@Entity
@Table(name = "physical_chemical_oil_analyzes")
public class PhysicalChemicalOilAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    @NotNull(message = "Should not be empty")
    @DateTimeFormat(pattern="dd.MM.yyyy")
    @JsonProperty("date_analysis")
    @Column(name = "date_analysis", nullable = false)
    private LocalDate dateAnalysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transformer", nullable = false)
    private Transformer transformer;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 2",
            integer = 4, fraction = 2)
    @JsonProperty("flash_point")
    @Column(name = "flash_point", nullable = false)
    private Double flashPoint;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 5, fraction = 5)
    @JsonProperty("acid_number")
    @Column(name = "acid_number", nullable = false)
    private Double acidNumber;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2",
            integer = 2, fraction = 0)
    @JsonProperty("cleanliness_class")
    @Column(name = "cleanliness_class", nullable = false)
    private Integer cleanlinessClass;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 5, fraction = 5)
    @JsonProperty("moisture_content")
    @Column(name = "moisture_content", nullable = false)
    private Double moistureContent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 3, fraction = 2)
    @JsonProperty("breakdown_voltage")
    @Column(name = "breakdown_voltage",nullable = false)
    private Double breakdownVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 5",
            integer = 2, fraction = 5)
    @JsonProperty("dielectric_loss_tangent")
    @Column(name = "dielectric_loss_tangent", nullable = false)
    private Double dielectricLossTangent;

    @NotNull(message = "Should not be empty")
    @Size(max = 40, message = "Should be less than 40 characters")
    @JsonProperty("protocol_name")
    @Column(name = "protocol_name", length = 60, nullable = false)
    private String protocolName;
}
