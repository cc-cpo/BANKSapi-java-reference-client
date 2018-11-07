package de.banksapi.client.services;

import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.services.internal.HttpHelper;

import java.net.URL;

class CustomerServiceBase extends AbstractAuthorizedBaseService {
    final static String PATH_FMT_BANKZUGAENGE = "bankzugaenge";
    final static String PATH_FMT_BANKZUGANG = "bankzugaenge/%s";
    final static String PATH_FMT_BANKZUGANG_SYNC = "bankzugaenge/%s/sync";
    final static String PATH_FMT_PRODUKT = "bankzugaenge/%s/%s";
    final static String PATH_FMT_KONTOUMSAETZE = "bankzugaenge/%s/%s/kontoumsaetze";
    final static String PATH_FMT_DEPOTPOSITIONEN = "bankzugaenge/%s/%s/depotpositionen";
    final static String PATH_FMT_UEBERWEISUNG = "ueberweisung/%s/%s";
    final static String PATH_FMT_SUBMIT_TAN = "ueberweisung/%s/%s/%s";


    CustomerServiceBase(OAuth2Token oAuth2Token) {
        super(oAuth2Token);
    }

    URL getCustomerContext() {
        return HttpHelper.buildUrl(super.getBanksapiBaseUrl(),  "customer/v2/");
    }
}
