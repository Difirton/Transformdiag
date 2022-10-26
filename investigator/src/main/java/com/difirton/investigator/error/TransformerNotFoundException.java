package com.difirton.investigator.error;

public class TransformerNotFoundException extends RuntimeException {

    public TransformerNotFoundException(Long id) {
        super("Transformer is not found: " + id);
    }
}
