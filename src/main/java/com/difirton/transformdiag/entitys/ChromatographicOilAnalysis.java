package com.difirton.transformdiag.entitys;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Data
@Table(name = "chromatographic_oil_analysis")
@RequiredArgsConstructor
public class ChromatographicOilAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_analysis")
    private Date dateAnalysis;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transformer")
    private Transformer transformer;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Should not be empty", integer = 10, fraction = 2)
    @Column(name = "hydrogen_gas_H2")
    private Integer hydrogenGasH2;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Should not be empty", integer = 10, fraction = 2)
    @Column(name = "carbon_monoxide_CO")
    private Integer carbonMonoxideCO;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Should not be empty", integer = 10, fraction = 2)
    @Column(name = "carbon_dioxide_CO2")
    private Integer carbonDioxideCO2;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Should not be empty", integer = 10, fraction = 2)
    @Column(name = "methane_CH4")
    private Integer methaneCH4;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Should not be empty", integer = 10, fraction = 2)
    @Column(name = "ethane_C2H6")
    private Integer ethaneC2H6;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Should not be empty", integer = 10, fraction = 2)
    @Column(name = "ethylene_C2H4")
    private Integer ethyleneC2H4;

    @NotNull(message = "Should not be empty")
    @PositiveOrZero(message = "Should be positive or zero")
    @Digits(message = "Should not be empty", integer = 10, fraction = 2)
    @Column(name = "acetylene_C2H2")
    private Integer acetyleneC2H2;
}
