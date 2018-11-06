package de.banksapi.client.services;

import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.services.internal.HttpHelper;
import de.banksapi.client.services.internal.IHTTPClient;
import de.banksapi.client.services.internal.IHTTPClientUnconfigured;

import java.net.URL;
import java.util.Base64;
import java.util.Objects;

import static de.banksapi.client.services.internal.HttpHelper.buildUrl;

/**
 * This service interfaces with the BANKSapi Auth OAuth2 API. This API is used to retrieve
 * OAuth2 tokens which are needed to authenticate requests to all other BANKSapi APIs.
 *
 * @see <a href="https://docs.banksapi.de/auth.html#oauth2-api-referenz">BANKSapi Auth OAuth2 API</a>
 */
public class OAuth2Service extends AbstractBaseService {
    private final static Base64.Encoder base64Encoder = Base64.getEncoder();

    IHTTPClient createUnauthenticatedHttpClient(URL requestUrl, String httpBasicAuth) {
        IHTTPClientUnconfigured client = getClientFactory().createClient(requestUrl);
        client.setHeader("Content-type", "application/x-www-form-urlencoded");
        client.setHeader("Authorization", "basic " + httpBasicAuth);
        client.setObjectMapperPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return client;
    }

    public OAuth2Token getClientToken(String clientUser, String clientPassword) {
        return getClientToken(createHttpBasicAuth(clientUser, clientPassword));
    }

    public OAuth2Token getClientToken(String httpBasicAuth) {
        Objects.requireNonNull(httpBasicAuth);

        URL tokenUrl = buildUrl(getAuthContext(), "oauth2/token");

        Response<OAuth2Token> response = createUnauthenticatedHttpClient(tokenUrl, httpBasicAuth)
                .post("grant_type=client_credentials", OAuth2Token.class);

        return response.getData();
    }

    public OAuth2Token getUserToken(String clientUser, String clientPassword, String username,
            String password) {
        return getUserToken(createHttpBasicAuth(clientUser, clientPassword), username, password);
    }

    public OAuth2Token getUserToken(String httpBasicAuth, String username, String password) {
        Objects.requireNonNull(httpBasicAuth);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        URL tokenUrl = buildUrl(getAuthContext(), "oauth2/token");
        String post = String.format("grant_type=password&username=%s&password=%s",
                username, password);

        Response<OAuth2Token> response = createUnauthenticatedHttpClient(tokenUrl, httpBasicAuth)
                .post(post, OAuth2Token.class);

        return response.getData();
    }

    private static String createHttpBasicAuth(String clientUser, String clientPassword) {
        Objects.requireNonNull(clientUser);
        Objects.requireNonNull(clientPassword);

        String userPassword = clientUser + ":" + clientPassword;
        return base64Encoder.encodeToString(userPassword.getBytes());
    }

    URL getAuthContext() {
        return HttpHelper.buildUrl(getBanksApiBase(),  "auth/");
    }
}
