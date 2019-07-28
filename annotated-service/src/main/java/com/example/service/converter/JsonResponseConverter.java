package com.example.service.converter;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpHeaders;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.ResponseConverterFunction;

public class JsonResponseConverter implements ResponseConverterFunction {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public HttpResponse convertResponse(ServiceRequestContext ctx,
                                        ResponseHeaders headers,
                                        @Nullable Object result,
                                        HttpHeaders trailers) throws Exception {
        if (result != null) {
            return HttpResponse.of(headers.toBuilder().contentType(MediaType.JSON_UTF_8).build(),
                                   HttpData.wrap(OBJECT_MAPPER.writeValueAsBytes(result)),
                                   trailers);
        }
        return ResponseConverterFunction.fallthrough();
    }
}
