package com.difirton.transformdiag.service.constant;

import java.util.Map;

import static com.difirton.transformdiag.service.constant.PhysicalChemicalOilParameter.*;

public enum PhysicalChemicalOilLimitValue {
    INSTANCE;

    private static final Map<PhysicalChemicalOilParameter, Double> BEFORE_150_KV_OIL_PARAMETER = Map.of(
            FLASH_POINT, 125d,
            ACID_NUMBER, 0.07,
            CLEANLINESS_CLASS, 10d,
            MOISTURE_CONTENT, 0.002,
            BREAKDOWN_VOLTAGE, 40d,
            DIELECTRIC_LOSS_TANGENT, 12d);
    private static final Map<PhysicalChemicalOilParameter, Double> BETWEEN_150_AND_550_KV_OIL_PARAMETER = Map.of(
            FLASH_POINT, 125d,
            ACID_NUMBER, 0.07,
            CLEANLINESS_CLASS, 10d,
            MOISTURE_CONTENT, 0.002,
            BREAKDOWN_VOLTAGE, 50d,
            DIELECTRIC_LOSS_TANGENT, 8d);
    private static final Map<PhysicalChemicalOilParameter, Double> AFTER_550_KV_OIL_PARAMETER = Map.of(
            FLASH_POINT, 125d,
            ACID_NUMBER, 0.07,
            CLEANLINESS_CLASS, 10d,
            MOISTURE_CONTENT, 0.002,
            BREAKDOWN_VOLTAGE, 60d,
            DIELECTRIC_LOSS_TANGENT, 3d);

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
