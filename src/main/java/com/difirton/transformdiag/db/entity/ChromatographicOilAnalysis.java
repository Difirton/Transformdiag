package com.difirton.transformdiag.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"transformer"})
@JsonIgnoreProperties(value = {"transformer"})
@Builder
@Entity
@Table(name = "chromatographic_oil_analyzes")
public class ChromatographicOilAnalysis {
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
    @JoinColumn(name = "transformer")
    private Transformer transformer;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Count of digits should be between", integer = 10, fraction = 2)
    @JsonProperty("hydrogen_gas_H2")
    @Column(name = "hydrogen_gas_H2", nullable = false)
    private Integer hydrogenGasH2;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 10, after less than 2",
            integer = 10, fraction = 2)
    @JsonProperty("carbon_monoxide_CO")
    @Column(name = "carbon_monoxide_CO", nullable = false)
    private Integer carbonMonoxideCO;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 10, after less than 2",
            integer = 10, fraction = 2)
    @JsonProperty("carbon_dioxide_CO2")
    @Column(name = "carbon_dioxide_CO2", nullable = false)
    private Integer carbonDioxideCO2;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 10, after less than 2",
            integer = 10, fraction = 2)
    @JsonProperty("methane_CH4")
    @Column(name = "methane_CH4", nullable = false)
    private Integer methaneCH4;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 10, after less than 2",
            integer = 10, fraction = 2)
    @JsonProperty("ethane_C2H6")
    @Column(name = "ethane_C2H6", nullable = false)
    private Integer ethaneC2H6;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 10, after less than 2",
            integer = 10, fraction = 2)
    @JsonProperty("ethylene_C2H4")
    @Column(name = "ethylene_C2H4", nullable = false)
    private Integer ethyleneC2H4;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "The number of digits before the decimal point should be no more than 10, after less than 2",
            integer = 10, fraction = 2)
    @JsonProperty("acetylene_C2H2")
    @Column(name = "acetylene_C2H2", nullable = false)
    private Integer acetyleneC2H2;

    @NotBlank(message = "Should not be blank")
    @Size(max = 40, message = "Should be less than 40 characters")
    @JsonProperty("protocol_name")
    @Column(name = "protocol_name", length = 60, nullable = false)
    private String protocolName;
}
