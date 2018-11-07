package de.banksapi.client.services;

import de.banksapi.client.model.incoming.oauth2.OAuth2Token;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public abstract class AbstractAuthorizedBaseService extends AbstractBaseService {

    private OAuth2Token oAuth2Token;

    public AbstractAuthorizedBaseService(OAuth2Token oAuth2Token) {
        Objects.requireNonNull(oAuth2Token);
        this.oAuth2Token = oAuth2Token;
    }

    protected IHTTPClient createAuthenticatedHttpClient(URL requestUrl) {
        final IHTTPClientUnconfigured client = getClientFactory().createClient(requestUrl);
        client.setHeader("Content-Type", "application/json");
        client.setHeader("Authorization", "Bearer " + getOAuth2Token().getAccessToken());
        client.setObjectMapperPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

        if (getCorrelationIdStrategy() != null) {
            final UUID correlationId = getCorrelationIdStrategy().getCorrelationId();
            Objects.requireNonNull(correlationId);
            client.setHeader("X-Correlation-ID", correlationId.toString());
        }

        return client;
    }

    final OAuth2Token getOAuth2Token() {
        return oAuth2Token;
    }
}
