package de.banksapi.client.services.internal;

import de.banksapi.client.services.HttpMethod;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public final class RequestDetails {

    private final HttpMethod httpMethod;
    private final URL requestUrl;
    private final Object requestBody;
    private final UUID correlationId;

    public RequestDetails(HttpMethod httpMethod, URL requestUrl, Object requestBody, UUID correlationId) {
        Objects.nonNull(httpMethod);
        Objects.nonNull(requestBody);
        this.httpMethod = httpMethod;
        this.requestUrl = requestUrl;
        this.requestBody = requestBody;
        this.correlationId = correlationId;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public URL getRequestUrl() {
        return requestUrl;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    /**
     * note: this is the correlation id sent with request - this may differ from the correlation id returned in response object
     */
    public UUID getCorrelationId() {
        return correlationId;
    }


    @Override
    public String toString() {
        if (correlationId != null) {
            return String.format("%s %s (correlationId %s)", httpMethod, requestUrl, correlationId);
        } else {
            return String.format("%s %s (no correlationId)", httpMethod, requestUrl);
        }
    }
}
