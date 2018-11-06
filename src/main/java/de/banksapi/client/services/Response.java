package de.banksapi.client.services;

import java.net.MalformedURLException;
import java.net.URL;

public interface Response<T> {

    Integer getHttpCode();

    T getData();

    String getError();

    String getLocation();

    URL getLocationAsUrl();

}