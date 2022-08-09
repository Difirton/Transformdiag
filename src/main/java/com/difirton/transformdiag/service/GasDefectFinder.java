package com.difirton.transformdiag.service;

public class GasDefectFinder {
    double MaxH2;
    double MaxCH4;
    double MaxC2H2;
    double MaxC2H4;
    double MaxC2H6;
    double MaxCO;
    double MaxCO2;

    public GasDefectFinder(Double upVoltage) {
        if (upVoltage < 700) {
            MaxH2 = 100;
            MaxCH4 = 100;
            MaxC2H2 = 10;
            MaxC2H4 = 100;
            MaxC2H6 = 50;
            MaxCO = 500;
            MaxCO2 = 2000;
        } else {
            MaxH2 = 30;
            MaxCH4 = 20;
            MaxC2H2 = 10;
            MaxC2H4 = 20;
            MaxC2H6 = 10;
            MaxCO = 500;
            MaxCO2 = 4000;
        }
    }
}
