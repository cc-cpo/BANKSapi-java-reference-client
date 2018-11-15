package de.banksapi.client.services;

import java.net.URL;

public interface IHTTPClientUnconfigured extends IHTTPClient {

    URL getRequestUrl();

    IHTTPClientUnconfigured setHeader(String key, String value);

    void setObjectMapperPropertyNamingStrategy(PropertyNamingStrategy namingStrategy);

}
