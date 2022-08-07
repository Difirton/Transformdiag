package com.difirton.transformdiag.db.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@Table(name = "transformers")
@RequiredArgsConstructor
public class Transformer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 5, max = 30, message = "KKS should be between 5 and 30 characters ")
    @Column(name = "KKS", length = 30)
    private String KKS;

    @Size(max = 20, message = "Should be less than 30 characters")
    @NotBlank(message = "Type should not be empty")
    @Column(name = "type", length = 20)
    private String type;

    @Size(max = 20, message = "Should be less than 20 characters")
    @NotBlank(message = "Factory number should not be empty")
    @Column(name = "factory_number", length = 20)
    private String factoryNumber;

    @OneToOne(mappedBy = "transformer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TransformerCharacteristics transformerCharacteristics;

    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL)
    private List<ChromatographicOilAnalysis> chromatographicOilAnalysis;

    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL)
    private List<PhysicalChemicalOilAnalysis> physicalChemicalOilAnalysis;
}
