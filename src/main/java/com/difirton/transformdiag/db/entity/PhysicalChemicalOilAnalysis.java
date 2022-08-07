package com.difirton.transformdiag.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonProperty("date_analysis")
    @Column(name = "date_analysis")
    private LocalDate dateAnalysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transformer")
    private Transformer transformer;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 2",
            integer = 4, fraction = 2)
    @JsonProperty("flash_point")
    @Column(name = "flash_point")
    private Double flashPoint;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 5, fraction = 5)
    @JsonProperty("acid_number")
    @Column(name = "acid_number")
    private Double acidNumber;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2",
            integer = 2, fraction = 0)
    @JsonProperty("cleanliness_class")
    @Column(name = "cleanliness_class")
    private Integer cleanlinessClass;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 5, fraction = 5)
    @JsonProperty("moisture_content")
    @Column(name = "moisture_content")
    private Double moistureContent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 5",
            integer = 3, fraction = 2)
    @JsonProperty("breakdown_voltage")
    @Column(name = "breakdown_voltage")
    private Double breakdownVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 5",
            integer = 2, fraction = 5)
    @JsonProperty("dielectric_loss_tangent")
    @Column(name = "dielectric_loss_tangent")
    private Double dielectricLossTangent;

    @Size(max = 40, message = "Should be less than 40 characters")
    @JsonProperty("protocol_name")
    @Column(name = "protocol_name", length = 40)
    private String protocolName;
}
