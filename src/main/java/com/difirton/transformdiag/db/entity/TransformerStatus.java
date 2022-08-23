package com.difirton.transformdiag.db.entity;

import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TransformerStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private Transformer transformer;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    @JsonProperty("gases_out_of_limit")
    private List<OilGas> gasesOutOfLimit = new ArrayList<>();

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    @JsonProperty("define_oil_parameter_defects")
    private List<PhysicalChemicalOilParameter> defineOilParameterDefects = new ArrayList<>();

    @JsonProperty("define_defect")
    private TypeDefect defineDefect;

    @JsonProperty("is_damaged_paper_insulation")
    private Boolean isDamagedPaperInsulation = false;

    @JsonProperty("recommended_days_between_oil_sampling")
    private Integer recommendedDaysBetweenOilSampling;
}
