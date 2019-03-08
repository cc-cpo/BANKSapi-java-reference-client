package de.banksapi.client.services.internal;

import de.banksapi.client.services.*;

import java.time.Instant;
import java.util.UUID;

public final class AroundRequestHandler implements IHTTPClient {

    private IHTTPClientUnconfigured wrapped;
    private IRequestFilter filter;
    private UUID correlationId;

    public void setFilter(IRequestFilter filter) {
        this.filter = filter;
    }

    public AroundRequestHandler(IHTTPClientUnconfigured wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public <T> Response<T> postForm(String postData, Class<T> responseClass) {
        debugRequest(new RequestDetails(HttpMethod.POST, wrapped.getRequestUrl(), postData, correlationId));
        Response<T> response = wrapped.postForm(postData, responseClass);
        debugResponse(response, HttpMethod.POST);
        return response;
    }

    @Override
    public <U, T> Response<T> post(U postData, Class<T> responseClass) {
        debugRequest(new RequestDetails(HttpMethod.POST, wrapped.getRequestUrl(), postData, correlationId));
        Response<T> response = wrapped.post(postData, responseClass);
        debugResponse(response, HttpMethod.POST);
        return response;
    }

    @Override
    public <U, T> Response<T> put(U putData, Class<T> responseClass) {
        debugRequest(new RequestDetails(HttpMethod.PUT, wrapped.getRequestUrl(), putData, correlationId));
        Response<T> response = wrapped.put(putData, responseClass);
        debugResponse(response, HttpMethod.PUT);
        return response;
    }

    @Override
    public <T> Response<T> put(Class<T> responseClass) {
        debugRequest(new RequestDetails(HttpMethod.PUT, wrapped.getRequestUrl(), null, correlationId));
        final Response<T> response = wrapped.put(responseClass);
        debugResponse(response, HttpMethod.PUT);
        return response;
    }


    @Override
    public Response<String> delete() {
        debugRequest(new RequestDetails(HttpMethod.DELETE, wrapped.getRequestUrl(), null, correlationId));
        Response<String> response = wrapped.delete();
        debugResponse(response, HttpMethod.DELETE);
        return response;
    }

    @Override
    public <T> Response<T> get(Class<T> responseClass) {
        debugRequest(new RequestDetails(HttpMethod.GET, wrapped.getRequestUrl(), null, correlationId));
        Response<T> response = wrapped.get(responseClass);
        debugResponse(response, HttpMethod.GET);
        return response;
    }

    /**
     * @param correlationId correlation id or null
     */
    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    private void debugRequest(RequestDetails requestDebugDetails) {
        final Instant requestStarted = Instant.now();
        if (filter == null) {
            return;
        }
        try {
            filter.preRequest(requestStarted, requestDebugDetails);
        } catch (final Exception ex) {
            throw new IllegalStateException("Error in request handler", ex);
        }
    }

    private <T> void debugResponse(final Response<T> response, final HttpMethod method) {
        if (filter == null) {
            return;
        }
        final Instant responseTimestamp = Instant.now();

        RequestDetails requestDebugDetails = new RequestDetails(method, wrapped.getRequestUrl(), null, correlationId);
        try {
            filter.onResponse(responseTimestamp, requestDebugDetails, response);
        } catch (final Exception ex) {
            throw new IllegalStateException("Error in response handler", ex);
        }
    }

}
