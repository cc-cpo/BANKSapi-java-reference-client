package de.banksapi.client;

import java.net.MalformedURLException;
import java.net.URL;

import static de.banksapi.client.services.internal.StringUtil.isBlank;

public class SimpleBANKSapi implements BANKSapi {
    private URL banksapiBaseUrl;

    public SimpleBANKSapi(URL banksapiBaseUrl) {
        this.banksapiBaseUrl = banksapiBaseUrl;
    }

    public SimpleBANKSapi(){
        try {
            String url = System.getProperty("BANKSAPI_BASE_URL");
            url = isBlank(url) ? "https://banksapi.io" : url;
            banksapiBaseUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getBanksapiBaseUrl() {
        return banksapiBaseUrl;
    }
}
