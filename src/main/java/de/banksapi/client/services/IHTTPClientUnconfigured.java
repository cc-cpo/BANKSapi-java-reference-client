package de.banksapi.client.services;

public interface IHTTPClientUnconfigured extends IHTTPClient {

    IHTTPClientUnconfigured setHeader(String key, String value);

    void setObjectMapperPropertyNamingStrategy(PropertyNamingStrategy namingStrategy);

}
