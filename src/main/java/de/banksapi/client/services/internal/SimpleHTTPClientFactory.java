package de.banksapi.client.services.internal;

import java.net.URL;

public class SimpleHTTPClientFactory implements IHTTPClientFactory {

    public IHTTPClientUnconfigured createClient(URL requestUrl) {
        return new SimpleHttpClient(requestUrl);
    }

}
