package com.difirton.investigator.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "transformer_characteristics")
public class TransformerCharacteristics {
    @Id
    private String id;

    private Transformer transformer;

    private Integer power;

    private Double upVoltage;

    private Double middleVoltage;

    private Double downVoltage;

    private Double upCurrent;

    private Double middleCurrent;

    private Double downCurrent;

    private Integer frequency;

    private Integer numberPhases;

    private String connectionDiagram;

    private Double shortCircuitVoltage;

    private Double shortCircuitLoss;

    private Double idleLoss;

    private Double noLoadCurrent;
}
