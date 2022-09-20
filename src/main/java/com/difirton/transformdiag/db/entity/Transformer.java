package com.difirton.transformdiag.db.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"transformerCharacteristics", "chromatographicOilAnalyses", "physicalChemicalOilAnalyses"})
@EqualsAndHashCode(of = {"KKS", "type", "factoryNumber"})
@JsonIgnoreProperties(value = {"chromatographicOilAnalyses", "physicalChemicalOilAnalyses"})
@Validated
@Entity
@Table(name = "TRANSFORMERS")
public class Transformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 30, message = "KKS should be between 5 and 30 characters ")
    @Column(name = "KKS", length = 30, unique = true)
    private String KKS;

    @Size(max = 20, message = "Should be less than 30 characters")
    @NotBlank(message = "Type should not be empty")
    @Column(name = "type_transformer", length = 20, nullable = false)
    private String type;

    @Size(max = 20, message = "Should be less than 20 characters")
    @NotBlank(message = "Factory number should not be empty")
    @JsonProperty("factory_number")
    @Column(name = "factory_number", length = 20, nullable = false)
    private String factoryNumber;

    @JsonProperty("transformer_characteristics")
    @OneToOne(mappedBy = "transformer", cascade = CascadeType.ALL)
    private TransformerCharacteristics transformerCharacteristics;

    @JsonProperty("chromatographic_oil_analyses")
    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChromatographicOilAnalysis> chromatographicOilAnalyses = new ArrayList<>();

    @JsonProperty("physical_chemical_oil_analyses")
    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhysicalChemicalOilAnalysis> physicalChemicalOilAnalyses = new ArrayList<>();

    @JsonProperty("transformer_status")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private TransformerStatus transformerStatus;

}
