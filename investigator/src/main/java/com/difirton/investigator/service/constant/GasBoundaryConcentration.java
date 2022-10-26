package com.difirton.investigator.service.constant;

import java.util.Map;

public enum GasBoundaryConcentration {
    INSTANCE;

    private static final Map<OilGas, Integer> BEFORE_750_GAS_BOUNDARY_CONCENTRATIONS = Map.of(OilGas.H2, 100,
                                                                                                OilGas.CO, 500,
                                                                                                OilGas.CO2, 2000,
                                                                                                OilGas.CH4, 100,
                                                                                                OilGas.C2H6, 50,
                                                                                                OilGas.C2H4, 100,
                                                                                                OilGas.C2H2, 10);
    private static final Map<OilGas, Integer> AFTER_750_GAS_BOUNDARY_CONCENTRATIONS = Map.of(OilGas.H2, 30,
                                                                                               OilGas.CO, 500,
                                                                                               OilGas.CO2, 4000,
                                                                                               OilGas.CH4, 20,
                                                                                               OilGas.C2H6, 10,
                                                                                               OilGas.C2H4, 20,
                                                                                               OilGas.C2H2, 10);

    public static Map<OilGas, Integer> getGasBoundaryConcentration(Double upVoltage) {
        if (upVoltage < 600) {
            return BEFORE_750_GAS_BOUNDARY_CONCENTRATIONS;
        } else {
            return AFTER_750_GAS_BOUNDARY_CONCENTRATIONS;
        }
    }
}
