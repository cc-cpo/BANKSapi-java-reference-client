package de.banksapi.client.services;

import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.services.internal.*;

import java.net.URL;
import java.util.Objects;

public abstract class AbstractAuthorizedBaseService extends AbstractBaseService {

    private OAuth2Token oAuth2Token;

    public AbstractAuthorizedBaseService(OAuth2Token oAuth2Token) {
        Objects.requireNonNull(oAuth2Token);
        this.oAuth2Token = oAuth2Token;
    }

    IHTTPClient createAuthenticatingHttpClient(URL requestUrl) {
        final IHTTPClientUnconfigured client = getClientFactory().createClient(requestUrl);
        client.setHeader("Content-type", "application/json");
        client.setHeader("Authorization", "bearer " + getOAuth2Token().getAccessToken());
        client.setObjectMapperPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        return client;
    }

    final OAuth2Token getOAuth2Token() {
        return oAuth2Token;
    }
}
