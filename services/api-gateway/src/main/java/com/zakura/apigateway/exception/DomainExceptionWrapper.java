/* Licensed under Apache-2.0 2021-2023 */
package com.zakura.apigateway.exception;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webflux.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
@Slf4j
public class DomainExceptionWrapper extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(
            ServerRequest request, ErrorAttributeOptions includeStackTrace) {
        final var error = getError(request);
        final var errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        //        errorAttributes.put(ErrorAttribute.TRACE_ID.value, tracer.traceId());
        if (error instanceof RecordNotFoundException errorStatus) {
            log.error("Caught an instance of: {}, err: {}", RecordNotFoundException.class, error);
            errorAttributes.replace(
                    ErrorAttribute.STATUS.value, errorStatus.getHttpStatus().value());
            errorAttributes.replace(
                    ErrorAttribute.ERROR.value, errorStatus.getHttpStatus().getReasonPhrase());
            errorAttributes.replace("message", errorStatus.getMessage());
            return errorAttributes;
        } else if (error instanceof UserAuthenticationFailedException errorStatus) {
            log.error(
                    "Caught an instance of: {}, err: {}",
                    UserAuthenticationFailedException.class,
                    error);
            errorAttributes.replace(
                    ErrorAttribute.STATUS.value, errorStatus.getHttpStatus().value());
            errorAttributes.replace(
                    ErrorAttribute.ERROR.value, errorStatus.getHttpStatus().getReasonPhrase());
            errorAttributes.replace("message", errorStatus.getMessage());
            return errorAttributes;
        }
        return errorAttributes;
    }

    enum ErrorAttribute {
        STATUS("status"),
        ERROR("error"),
        TRACE_ID("traceId");

        private final String value;

        ErrorAttribute(String value) {
            this.value = value;
        }
    }
}
