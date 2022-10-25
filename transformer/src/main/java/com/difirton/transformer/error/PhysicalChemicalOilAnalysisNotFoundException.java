package com.difirton.transformer.error;

public class PhysicalChemicalOilAnalysisNotFoundException extends RuntimeException {
    public PhysicalChemicalOilAnalysisNotFoundException(Long id) {
        super("Physical chemical oil analysis is not found: " + id);
    }
}