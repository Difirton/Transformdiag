package com.difirton.transformdiag.service;

import com.difirton.transformdiag.db.entity.ChromatographicOilAnalysis;
import com.difirton.transformdiag.db.entity.PhysicalChemicalOilAnalysis;
import com.difirton.transformdiag.service.constant.GasBoundaryConcentration;
import com.difirton.transformdiag.service.constant.OilGas;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilLimitValue;
import com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.difirton.transformdiag.service.constant.OilGas.*;
import static com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter.*;

public class OilStandardsComparator {
    private final Map<OilGas, Integer> limitGasContent;
    private final Map<PhysicalChemicalOilParameter, Double> limitPhysicalChemicalOilParameter;

    public OilStandardsComparator(Double transformerUpVoltage) {
        this.limitGasContent = GasBoundaryConcentration.getGasBoundaryConcentration(transformerUpVoltage);
        this.limitPhysicalChemicalOilParameter = PhysicalChemicalOilLimitValue
                .getPhysicalChemicalOilLimitValue(transformerUpVoltage);
    }

    public List<OilGas> compareWithStandardGasContent(ChromatographicOilAnalysis analysis) {
        List<OilGas> excessGases = new ArrayList<>();
        if (analysis.getHydrogenGasH2() > limitGasContent.get(H2)) {
            excessGases.add(H2);
        }
        if (analysis.getMethaneCH4() > limitGasContent.get(CH4)) {
            excessGases.add(CH4);
        }
        if (analysis.getAcetyleneC2H2() > limitGasContent.get(C2H2)) {
            excessGases.add(C2H2);
        }
        if (analysis.getEthyleneC2H4() > limitGasContent.get(C2H4)) {
            excessGases.add(C2H4);
        }
        if (analysis.getEthaneC2H6() > limitGasContent.get(C2H6)) {
            excessGases.add(C2H6);
        }
        if (analysis.getCarbonMonoxideCO() > limitGasContent.get(CO)) {
            excessGases.add(CO);
        }
        if (analysis.getCarbonDioxideCO2() > limitGasContent.get(CO2)) {
            excessGases.add(CO2);
        }
        return excessGases;
    }

    public List<PhysicalChemicalOilParameter> compareWithStandardOilParameters(PhysicalChemicalOilAnalysis analysis) {
        List<PhysicalChemicalOilParameter> excessOilParameters = new ArrayList<>();
        if (analysis.getFlashPoint() > limitPhysicalChemicalOilParameter.get(FLASH_POINT)) {
            excessOilParameters.add(FLASH_POINT);
        }
        if (analysis.getAcidNumber() > limitPhysicalChemicalOilParameter.get(ACID_NUMBER)) {
            excessOilParameters.add(ACID_NUMBER);
        }
        if (analysis.getCleanlinessClass() > limitPhysicalChemicalOilParameter.get(CLEANLINESS_CLASS)) {
            excessOilParameters.add(CLEANLINESS_CLASS);
        }
        if (analysis.getMoistureContent() > limitPhysicalChemicalOilParameter.get(MOISTURE_CONTENT)) {
            excessOilParameters.add(MOISTURE_CONTENT);
        }
        if (analysis.getBreakdownVoltage() > limitPhysicalChemicalOilParameter.get(BREAKDOWN_VOLTAGE)) {
            excessOilParameters.add(BREAKDOWN_VOLTAGE);
        }
        if (analysis.getDielectricLossTangent() > limitPhysicalChemicalOilParameter.get(DIELECTRIC_LOSS_TANGENT)) {
            excessOilParameters.add(DIELECTRIC_LOSS_TANGENT);
        }
        return excessOilParameters;
    }
}
