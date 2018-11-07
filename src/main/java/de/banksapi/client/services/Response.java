package de.banksapi.client.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public interface Response<T> {

    Integer getHttpCode();

    T getData();

    String getError();

    UUID getCorrelationId();

    String getLocation();

    URL getLocationAsUrl();

}