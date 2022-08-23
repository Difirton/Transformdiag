package com.difirton.transformdiag.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TransformerNotFoundException.class)
    public void handleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(TransformerUnSupportedFieldPatchException.class)
    public void unSupportedFieldPatch(HttpServletResponse response) throws  IOException {
        response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @ExceptionHandler(EmptyListOfAnalysisException.class)
    public void badRequest(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
