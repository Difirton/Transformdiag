package com.difirton.investigator.db.entity;

import com.difirton.investigator.service.constant.OilGas;
import com.difirton.investigator.service.constant.PhysicalChemicalOilParameter;
import com.difirton.investigator.service.constant.TypeDefect;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection  = "transformer_status")
public class TransformerStatus {
    @Id
    private String id;

    private Transformer transformer;

    private List<OilGas> gasesOutOfLimit = new ArrayList<>();

    private List<PhysicalChemicalOilParameter> defineOilParameterDefects = new ArrayList<>();

    private TypeDefect defineDefect;

    private Boolean isDamagedPaperInsulation;

    private Integer recommendedDaysBetweenOilSampling;
}
