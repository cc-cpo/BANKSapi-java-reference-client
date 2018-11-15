package de.banksapi.client.services;

import de.banksapi.client.services.internal.RequestDetails;

import java.time.Instant;

public interface IRequestFilter {

    void preRequest(Instant requestStarted, RequestDetails requestDetails);

    <T> void onResponse(Instant responseTimestamp, RequestDetails requestDetails, Response<T> response);
}
