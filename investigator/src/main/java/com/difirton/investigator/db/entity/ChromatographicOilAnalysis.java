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
@Document(collection = "chromatographic_oil_analyzes")
public class ChromatographicOilAnalysis {
    @Id
    private Long id;

    @DateTimeFormat(pattern="dd.MM.yyyy")
    private LocalDate dateAnalysis;

    private Transformer transformer;

    private Integer hydrogenGasH2;

    private Integer carbonMonoxideCO;

    private Integer carbonDioxideCO2;

    private Integer methaneCH4;

    private Integer ethaneC2H6;

    private Integer ethyleneC2H4;

    private Integer acetyleneC2H2;

    private String protocolName;
}
