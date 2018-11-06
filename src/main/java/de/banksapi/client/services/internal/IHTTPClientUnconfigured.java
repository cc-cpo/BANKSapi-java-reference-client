package de.banksapi.client.services.internal;

import de.banksapi.client.services.PropertyNamingStrategy;

public interface IHTTPClientUnconfigured extends IHTTPClient {

    IHTTPClientUnconfigured setHeader(String key, String value);

    void setObjectMapperPropertyNamingStrategy(PropertyNamingStrategy namingStrategy);

}
