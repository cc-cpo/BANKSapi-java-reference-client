package de.banksapi.client.services.internal;

import java.net.URL;

public class DefaultHTTPClientFactory implements IHTTPClientFactory {

    public IHTTPClientUnconfigured createClient(URL requestUrl) {
        return new DefaultStatefulHttpClient(requestUrl);
    }

}
