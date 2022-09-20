package com.difirton.transformdiag.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "transformer", ignoreUnknown = true)
@EqualsAndHashCode(exclude = "transformer")
@Builder
@Entity(name = "transformer_characteristics")
@Table(name = "transformer_characteristics")
public class TransformerCharacteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private Transformer transformer;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 8",
            integer = 8, fraction = 0)
    @Column(name = "power",nullable = false)
    private Integer power;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 3",
            integer = 4, fraction = 3)
    @JsonProperty("up_voltage")
    @Column(name = "up_voltage", nullable = false)
    private Double upVoltage;

    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 3",
            integer = 4, fraction = 3)
    @JsonProperty("middle_voltage")
    @Column(name = "middle_voltage")
    private Double middleVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 3",
            integer = 4, fraction = 3)
    @JsonProperty("down_voltage")
    @Column(name = "down_voltage", nullable = false)
    private Double downVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 7, after less than 1",
            integer = 7, fraction = 1)
    @JsonProperty("up_current")
    @Column(name = "up_current", nullable = false)
    private Double upCurrent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 7, after less than 1",
            integer = 7, fraction = 1)
    @JsonProperty("middle_current")
    @Column(name = "middle_current")
    private Double middleCurrent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 7, after less than 1",
            integer = 7, fraction = 1)
    @JsonProperty("down_current")
    @Column(name = "down_current")
    private Double downCurrent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 0",
            integer = 2, fraction = 0)
    @JsonProperty("frequency")
    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 1, after less than 0",
            integer = 1, fraction = 0)
    @JsonProperty("number_phases")
    @Column(name = "number_phases", nullable = false)
    private Integer numberPhases;

    @Size(max = 20, message = "Should be less than 20 characters")
    @NotBlank(message = "Connection diagram should not be empty")
    @JsonProperty("connection_diagram")
    @Column(name = "connection_diagram", length = 20, nullable = false)
    private String connectionDiagram;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 2",
            integer = 2, fraction = 2)
    @JsonProperty("short_circuit_voltage")
    @Column(name = "short_circuit_voltage", nullable = false)
    private Double shortCircuitVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 3",
            integer = 5, fraction = 3)
    @JsonProperty("short_circuit_loss")
    @Column(name = "short_circuit_loss")
    private Double shortCircuitLoss;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 6, after less than 3",
            integer = 6, fraction = 3)
    @JsonProperty("idle_loss")
    @Column(name = "idle_loss")
    private Double idleLoss;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 4",
            integer = 2, fraction = 4)
    @JsonProperty("no_load_current")
    @Column(name = "no_load_current")
    private Double noLoadCurrent;
}
