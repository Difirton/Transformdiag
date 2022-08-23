package com.difirton.transformdiag.web.dto.response;

import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName("transformer_defect_response")
public class TransformerDefectResponseDto {
    @JsonProperty("gases_out_limit")
    private List<OilGas> gasesOutOfLimit;

    @JsonProperty("define_oil_parameter_defects")
    private List<PhysicalChemicalOilParameter> defineOilParameterDefects;

    @JsonProperty("define_defect")
    private TypeDefect defineDefect;

    @JsonProperty("is_damaged_paper_insulation")
    private boolean isDamagedPaperInsulation;

    @JsonProperty("recommended_days_between_oil_sampling")
    private int recommendedDaysBetweenOilSampling;
}
