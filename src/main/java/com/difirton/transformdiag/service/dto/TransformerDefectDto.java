package com.difirton.transformdiag.service.dto;

import com.difirton.transformdiag.db.entity.Transformer;
import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;
import com.difirton.transformdiag.service.constant.TypeDefect;

import java.util.ArrayList;
import java.util.List;

public class TransformerDefectDto {
    private final Transformer transformer;
    private final List<OilGas> gasesOutOfLimit = new ArrayList<>();;
    private final List<PhysicalChemicalOilParameter> defineOilParameterDefects = new ArrayList<>();;
    private TypeDefect defineDefect;
    private boolean isDamagedPaperInsulation = false;
    private int recommendedDaysBetweenOilSampling;

    public TransformerDefectDto(Transformer transformer) {
        this.transformer = transformer;
    }

    public Transformer getTransformer() {
        return transformer;
    }

    public List<OilGas> getGasesOutOfLimit() {
        return gasesOutOfLimit;
    }

    public List<PhysicalChemicalOilParameter> getDefineOilParameterDefects() {
        return defineOilParameterDefects;
    }

    public TypeDefect getDefineDefect() {
        return defineDefect;
    }

    public boolean isDamagedPaperInsulation() {
        return isDamagedPaperInsulation;
    }

    public int getRecommendedDaysBetweenOilSampling() {
        return recommendedDaysBetweenOilSampling;
    }

    public void setDefineDefect(TypeDefect defineDefect) {
        this.defineDefect = defineDefect;
    }

    public void setDamagedPaperInsulation(boolean damagedPaperInsulation) {
        isDamagedPaperInsulation = damagedPaperInsulation;
    }

    public void setRecommendedDaysBetweenOilSampling(int recommendedDaysBetweenOilSampling) {
        this.recommendedDaysBetweenOilSampling = recommendedDaysBetweenOilSampling;
    }
}
