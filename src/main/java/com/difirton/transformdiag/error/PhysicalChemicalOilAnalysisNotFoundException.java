package com.difirton.transformdiag.error;

public class PhysicalChemicalOilAnalysisNotFoundException extends RuntimeException {
    public PhysicalChemicalOilAnalysisNotFoundException(Long id) {
        super("Physical chemical oil analysis is not found: " + id);
    }
}