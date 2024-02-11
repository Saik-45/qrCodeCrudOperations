package com.sai.qrCodeGenerator.Advise;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.util.Locale;

public interface ErrorResponse {
    HttpStatusCode getStatusCode();
    HttpHeaders getHeaders();
    ProblemDetail getBody();
    String getTypeMessageCode();
    String getTitleMessageCode();
    String getDetailMessageCode();
    Object[] getDetailMessageArguments();
    Object[] getDetailMessageArguments(MessageSource messageSource, Locale locale);
    ProblemDetail updateAndGetBody(MessageSource messageSource, Locale locale);
}
