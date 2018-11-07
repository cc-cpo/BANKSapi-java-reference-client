package de.banksapi.client.services.internal;

import de.banksapi.client.services.IHTTPClientFactory;
import de.banksapi.client.services.IHTTPClientUnconfigured;

import java.net.URL;

public class SimpleHTTPClientFactory implements IHTTPClientFactory {

    public IHTTPClientUnconfigured createClient(URL requestUrl) {
        return new SimpleHttpClient(requestUrl);
    }

}
