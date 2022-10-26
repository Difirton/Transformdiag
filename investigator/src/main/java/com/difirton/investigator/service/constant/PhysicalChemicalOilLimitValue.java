package com.difirton.investigator.service.constant;

import java.util.Map;

public enum PhysicalChemicalOilLimitValue {
    INSTANCE;

    private static final Map<PhysicalChemicalOilParameter, Double> BEFORE_150_KV_OIL_PARAMETER = Map.of(
            PhysicalChemicalOilParameter.FLASH_POINT, 125d,
            PhysicalChemicalOilParameter.ACID_NUMBER, 0.15,
            PhysicalChemicalOilParameter.CLEANLINESS_CLASS, 10d,
            PhysicalChemicalOilParameter.MOISTURE_CONTENT, 0.002,
            PhysicalChemicalOilParameter.BREAKDOWN_VOLTAGE, 40d,
            PhysicalChemicalOilParameter.DIELECTRIC_LOSS_TANGENT, 12d);
    private static final Map<PhysicalChemicalOilParameter, Double> BETWEEN_150_AND_550_KV_OIL_PARAMETER = Map.of(
            PhysicalChemicalOilParameter.FLASH_POINT, 125d,
            PhysicalChemicalOilParameter.ACID_NUMBER, 0.15,
            PhysicalChemicalOilParameter.CLEANLINESS_CLASS, 10d,
            PhysicalChemicalOilParameter.MOISTURE_CONTENT, 0.002,
            PhysicalChemicalOilParameter.BREAKDOWN_VOLTAGE, 50d,
            PhysicalChemicalOilParameter.DIELECTRIC_LOSS_TANGENT, 8d);
    private static final Map<PhysicalChemicalOilParameter, Double> AFTER_550_KV_OIL_PARAMETER = Map.of(
            PhysicalChemicalOilParameter.FLASH_POINT, 125d,
            PhysicalChemicalOilParameter.ACID_NUMBER, 0.15,
            PhysicalChemicalOilParameter.CLEANLINESS_CLASS, 10d,
            PhysicalChemicalOilParameter.MOISTURE_CONTENT, 0.002,
            PhysicalChemicalOilParameter.BREAKDOWN_VOLTAGE, 60d,
            PhysicalChemicalOilParameter.DIELECTRIC_LOSS_TANGENT, 3d);

    public static Map<PhysicalChemicalOilParameter, Double> getPhysicalChemicalOilLimitValue(Double upVoltage) {
        if (upVoltage <= 150) {
            return BEFORE_150_KV_OIL_PARAMETER;
        } else if (upVoltage <= 550) {
            return BETWEEN_150_AND_550_KV_OIL_PARAMETER;
        } else {
            return AFTER_550_KV_OIL_PARAMETER;
        }
    }
}
