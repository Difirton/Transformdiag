package com.difirton.transformdiag.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"transformerCharacteristics", "chromatographicOilAnalysis", "physicalChemicalOilAnalysis"})
@EqualsAndHashCode(of = {"KKS", "type", "factoryNumber"})
@JsonIgnoreProperties(value = {"chromatographicOilAnalysis", "physicalChemicalOilAnalysis"})
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
    @Column(name = "TYPE_TRANSFORMER", length = 20)
    private String type;

    @Size(max = 20, message = "Should be less than 20 characters")
    @NotBlank(message = "Factory number should not be empty")
    @JsonProperty("factory_number")
    @Column(name = "factory_number", length = 20)
    private String factoryNumber;

    @JsonProperty("transformer_characteristics")
    @OneToOne(mappedBy = "transformer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TransformerCharacteristics transformerCharacteristics;

    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL)
    private List<ChromatographicOilAnalysis> chromatographicOilAnalysis = new ArrayList<>();

    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL)
    private List<PhysicalChemicalOilAnalysis> physicalChemicalOilAnalysis = new ArrayList<>();
}
