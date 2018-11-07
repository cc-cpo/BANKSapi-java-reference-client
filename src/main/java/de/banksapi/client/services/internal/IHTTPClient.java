package de.banksapi.client.services.internal;

import de.banksapi.client.services.Response;

public interface IHTTPClient {

    /**
     * post as url-encoded form
     * note: all other methods use application/json requests
     */
    <T> Response<T> postForm(String postData, Class<T> responseClass);

    <U, T> Response<T> post(U postData, Class<T> responseClass);

    <U, T> Response<T> put(U putData, Class<T> responseClass);

    <T> Response<T> put(Class<T> responseClass);

    Response<String> delete();

    <T> Response<T> get(Class<T> responseClass);
}
