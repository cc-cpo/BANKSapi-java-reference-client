package de.banksapi.client;

import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.model.incoming.providers.Provider;
import de.banksapi.client.model.incoming.providers.ProviderList;
import de.banksapi.client.services.OAuth2Service;
import de.banksapi.client.services.ProvidersService;
import de.banksapi.client.services.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static de.banksapi.client.TestAuthData.*;

public class ProvidersServiceTest implements BanksapiTest{

    private static ProvidersService providersService;

    @Before
    public void setUp() throws Exception {
        OAuth2Service oAuth2Service = new OAuth2Service();
        injectTestConfig(oAuth2Service);
        OAuth2Token token = oAuth2Service.getUserToken(CLIENT_USERNAME, CLIENT_PASSWORD,
                USERNAME, PASSWORD);
        providersService = new ProvidersService(token);
        injectTestConfig(providersService);
    }

    @Test
    public void testGetProviders() {
        Response<ProviderList> response = providersService.getProviders();
        Assert.assertTrue(response.getData().size() > 1);
    }

    @Test
    public void testGetDemoProvider() {
        Response<Provider> response = providersService.getProvider(
                UUID.fromString("00000000-0000-0000-0000-000000000000"));
        Provider provider = response.getData();

        String demoProviderName = "Demo Provider";
        String demoBic = "DEMO1234";

        Assert.assertEquals(demoProviderName, provider.getName());
        Assert.assertTrue(provider.isConsumerRelevant());
        Assert.assertEquals(demoBic, provider.getBic());
    }

}
