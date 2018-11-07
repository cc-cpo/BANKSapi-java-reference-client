package de.banksapi.client;

import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.services.OAuth2Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.banksapi.client.TestAuthData.*;

public class OAuth2ServiceTest implements BanksapiTest {

    private static de.banksapi.client.services.OAuth2Service oAuth2Service;

    @Before
    public void setUp() throws Exception {
        oAuth2Service = new OAuth2Service();
        injectTestConfig(oAuth2Service);
    }

    @Test
    public void testGetClientToken() {
        OAuth2Token token = oAuth2Service.getClientToken(CLIENT_USERNAME, CLIENT_PASSWORD);
        Assert.assertNotNull(token);
    }

    @Test
    public void testGetUserToken() {
        OAuth2Token token = oAuth2Service.getUserToken(CLIENT_USERNAME, CLIENT_PASSWORD,
                USERNAME, PASSWORD);
        Assert.assertNotNull(token);
    }

}
