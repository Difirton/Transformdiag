package com.difirton.investigator.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "transformers")
public class Transformer {
    @Id
    private String id;

    private String KKS;

    private String type;

    private String factoryNumber;

    private TransformerCharacteristics transformerCharacteristics;

    @Builder.Default
    private List<ChromatographicOilAnalysis> chromatographicOilAnalyses = new ArrayList<>();

    @Builder.Default
    private List<PhysicalChemicalOilAnalysis> physicalChemicalOilAnalyses = new ArrayList<>();

    private TransformerStatus transformerStatus;

}
