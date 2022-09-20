package com.difirton.transformdiag.db.entity;

import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = "transformer", ignoreUnknown = true)
@EqualsAndHashCode(exclude = "transformer")
@Builder
@Entity
@Table(name = "transformer_status")
public class TransformerStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "transformerStatus")
    @JoinColumn(name = "transformer_id", referencedColumnName = "id")
    @ToString.Exclude
    private Transformer transformer;

    @Builder.Default
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Enumerated(value = EnumType.STRING)
    @JsonProperty("gases_out_of_limit")
    @Column(name = "gases_out_of_limit")
    private List<OilGas> gasesOutOfLimit = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Enumerated(value = EnumType.STRING)
    @JsonProperty("define_oil_parameter_defects")
    @Column(name = "define_oil_parameter_defects")
    private List<PhysicalChemicalOilParameter> defineOilParameterDefects = new ArrayList<>();

    @JsonProperty("define_defect")
    @Column(name = "define_defect", nullable = false)
    private TypeDefect defineDefect;

    @JsonProperty("is_damaged_paper_insulation")
    @Column(name = "is_damaged_paper_insulation")
    private Boolean isDamagedPaperInsulation;

    @JsonProperty("recommended_days_between_oil_sampling")
    @Column(name = "recommended_days_between_oil_sampling")
    private Integer recommendedDaysBetweenOilSampling;
}
