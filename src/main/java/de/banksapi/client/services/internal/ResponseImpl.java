package de.banksapi.client.services.internal;

import de.banksapi.client.services.Response;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class ResponseImpl<T> implements Response<T> {

    Integer httpCode;
    T data;
    String error;
    UUID correlationId;
    String location;

    ResponseImpl() {
    }

    public ResponseImpl(Integer httpCode, T data, String error, UUID correlationId, String location) {
        this.httpCode = httpCode;
        this.data = data;
        this.error = error;
        this.correlationId = correlationId;
        this.location = location;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    /**
     * @return correlation id from header X-Correlation-ID or null
     */
    public UUID getCorrelationId() {
        return correlationId;
    }

    public String getLocation() {
        return location;
    }

    public URL getLocationAsUrl() {
        try {
            return new URL(location);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Location '" + location + "' holds an invalid URL", e);
        }
    }

    @Override
    public String toString() {
        return String.format("Response code <%d> with data <%s> (%s) and correlation id <%s>",
            httpCode, data, error != null ? String.format("error <%s>", error) : "no error string", correlationId);
    }
}