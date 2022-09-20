package com.difirton.transformdiag.error;

public class ChromatographicOilAnalysisNotFoundException extends RuntimeException {
    public ChromatographicOilAnalysisNotFoundException(Long id) {
        super("Chromatographic oil analysis is not found: " + id);
    }
}
