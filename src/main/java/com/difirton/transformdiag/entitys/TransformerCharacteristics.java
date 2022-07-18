package com.difirton.transformdiag.entitys;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "transformer_characteristics")
public class TransformerCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transformer_id")
    private Transformer transformer;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 8",
            integer = 8, fraction = 0)
    @Column(name = "power")
    private Integer power;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 3",
            integer = 4, fraction = 3)
    @Column(name = "up_voltage")
    private Double upVoltage;

    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 3",
            integer = 4, fraction = 3)
    @Column(name = "middle_voltage")
    private Double middleVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 4, after less than 3",
            integer = 4, fraction = 3)
    @Column(name = "down_voltage")
    private Double downVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 7, after less than 1",
            integer = 7, fraction = 1)
    @Column(name = "up_current")
    private Double upCurrent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 7, after less than 1",
            integer = 7, fraction = 1)
    @Column(name = "middle_current")
    private Double middleCurrent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 7, after less than 1",
            integer = 7, fraction = 1)
    @Column(name = "down_current")
    private Double downCurrent;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 0",
            integer = 2, fraction = 0)
    @Column(name = "frequency")
    private Integer frequency;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 1, after less than 0",
            integer = 1, fraction = 0)
    @Column(name = "number_phases")
    private Integer numberPhases;

    @Size(max = 20, message = "Should be less than 20 characters")
    @NotBlank(message = "Connection diagram should not be empty")
    @Column(name = "connection_diagram", length = 20)
    private String connectionDiagram;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 2",
            integer = 2, fraction = 2)
    @Column(name = "short_circuit_voltage")
    private Double shortCircuitVoltage;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 5, after less than 3",
            integer = 5, fraction = 3)
    @Column(name = "short_circuit_loss")
    private Double shortCircuitLoss;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 6, after less than 3",
            integer = 6, fraction = 3)
    @Column(name = "idle_loss")
    private Double idleLoss;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 2, after less than 4",
            integer = 2, fraction = 4)
    @Column(name = "no_load_current")
    private Double noLoadCurrent;
}
