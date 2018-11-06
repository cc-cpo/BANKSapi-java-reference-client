package de.banksapi.client.services.internal;

import java.net.URL;

public interface IHTTPClientFactory {

    /**
     * single request scoped http client
     */
    IHTTPClientUnconfigured createClient(URL requestUrl);
}
