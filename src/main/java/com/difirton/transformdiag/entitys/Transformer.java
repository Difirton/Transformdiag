package com.difirton.transformdiag.entitys;

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

    @Size(min = 5, message = "KKS should be more than 5 characters")
    @Column(name = "KKS", length = 50)
    private String KKS;

    @NotBlank(message = "Type should not be empty")
    @Column(name = "type", length = 50)
    private String type;

    @NotBlank(message = "Factory number should not be empty")
    @Column(name = "factory_number", length = 50)
    private String factoryNumber;

    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL)
    private List<ChromatographicOilAnalysis> chromatographicOilAnalysis;

    @OneToMany(mappedBy = "transformer", cascade = CascadeType.ALL)
    private List<PhysicalChemicalOilAnalysis> physicalChemicalAnalysisOils;
}
