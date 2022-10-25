package com.difirton.transformer.error;

import java.util.Set;

public class TransformerUnSupportedFieldPatchException extends RuntimeException {

    public TransformerUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }
}
