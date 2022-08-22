package com.difirton.transformdiag.error;

public class EmptyListOfAnalysisException extends RuntimeException {

    public EmptyListOfAnalysisException(Long id) {
        super("Transformer with " + id + " hasn't any analysis");
    }
}
