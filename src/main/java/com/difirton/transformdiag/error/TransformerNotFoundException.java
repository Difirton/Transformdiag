package com.difirton.transformdiag.error;

public class TransformerNotFoundException extends RuntimeException {

    public TransformerNotFoundException(Long id) {
        super("Transformer is not found: " + id);
    }
}
