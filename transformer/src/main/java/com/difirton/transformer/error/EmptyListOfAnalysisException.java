package com.difirton.transformer.error;

public class EmptyListOfAnalysisException extends RuntimeException {

    public EmptyListOfAnalysisException(Long id) {
        super("Transformer with id = " + id + " hasn't any analysis");
    }
}
