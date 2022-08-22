package com.difirton.transformdiag.service.constant;

import java.util.Map;

import static com.difirton.transformdiag.service.constant.OilGas.*;

public enum GasBoundaryConcentration {
    INSTANCE;

    private static final Map<OilGas, Integer> BEFORE_750_GAS_BOUNDARY_CONCENTRATIONS = Map.of(H2, 100,
                                                                                                CO, 500,
                                                                                                CO2, 2000,
                                                                                                CH4, 100,
                                                                                                C2H6, 50,
                                                                                                C2H4, 100,
                                                                                                C2H2, 10);
    private static final Map<OilGas, Integer> AFTER_750_GAS_BOUNDARY_CONCENTRATIONS = Map.of(H2, 30,
                                                                                               CO, 500,
                                                                                               CO2, 4000,
                                                                                               CH4, 20,
                                                                                               C2H6, 10,
                                                                                               C2H4, 20,
                                                                                               C2H2, 10);

    public static Map<OilGas, Integer> getGasBoundaryConcentration(Double upVoltage) {
        if (upVoltage < 600) {
            return BEFORE_750_GAS_BOUNDARY_CONCENTRATIONS;
        } else {
            return AFTER_750_GAS_BOUNDARY_CONCENTRATIONS;
        }
    }
}
