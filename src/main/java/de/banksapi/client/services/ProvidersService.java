package de.banksapi.client.services;

import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.model.incoming.providers.Provider;
import de.banksapi.client.model.incoming.providers.ProviderList;
import de.banksapi.client.services.internal.DefaultStatefulHttpClient;
import de.banksapi.client.services.internal.HttpHelper;

import java.net.URL;
import java.util.UUID;

import static de.banksapi.client.services.internal.HttpHelper.buildUrl;

/**
 * This service interfaces with the Banks/Connect Providers API. This API is used to retrieve
 * information about {@link Provider}s.
 *
 * @see <a href="https://docs.banksapi.de/providers.html">Banks/Connect Providers API</a>
 */
public class ProvidersService extends AbstractAuthorizedBaseService {

    /**
     * Creates a new instance of the providers service.
     *
     * @param oAuth2Token a valid OAuth2 token to send along all requests
     */
    public ProvidersService(OAuth2Token oAuth2Token) {
        super(oAuth2Token);
    }

    public Response<ProviderList> getProviders() {
        return createAuthenticatingHttpClient(getProvidersContext()).get(ProviderList.class);
    }

    public Response<Provider> getProvider(UUID providerId) {
        URL providerUrl = buildUrl(getProvidersContext(), providerId.toString());
        return createAuthenticatingHttpClient(providerUrl).get(Provider.class);
    }

    URL getProvidersContext() {
        return HttpHelper.buildUrl(super.getBanksApiBase(), "providers/v2/");
    }
}
