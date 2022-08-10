package com.difirton.transformdiag.service;

import com.difirton.transformdiag.service.constant.GasBoundaryConcentration;
import com.difirton.transformdiag.service.constant.OilGases;

import java.util.Map;

public class GasDefectFinder {
    private Integer transformerUpVoltage;
    private Map<OilGases, Integer> limitGasContent;

    GasDefectFinder(Integer transformerUpVoltage) {
        this.transformerUpVoltage = transformerUpVoltage;
        limitGasContent = GasBoundaryConcentration.getGasBoundaryConcentration(transformerUpVoltage);
    }

    boolean checkDefect() {

    }
}
