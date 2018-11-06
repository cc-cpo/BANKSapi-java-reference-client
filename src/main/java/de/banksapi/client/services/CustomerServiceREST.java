package de.banksapi.client.services;

import de.banksapi.client.crypto.CryptoService;
import de.banksapi.client.model.incoming.access.*;
import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.model.outgoing.access.LoginCredentialsMap;
import de.banksapi.client.model.outgoing.access.Ueberweisung;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.banksapi.client.services.internal.HttpHelper.buildUrl;

/**
 * This service interfaces with the Banks/Connect Customer API. This API provides all relevant
 * customer information, such as banking accounts, products and turnovers.
 * <p>This service uses the APIs in the common REST way.</p>
 *
 * @see <a href="https://docs.banksapi.de/customer.html">Banks/Connect Customer API</a>
 */
public class CustomerServiceREST extends CustomerServiceBase {

    private CryptoService cryptoService;

    /**
     * Creates a new instance of the customer service without encryption capability.
     * <p>When using this constructor credentials provided to
     * {@link #addBankzugaenge(LoginCredentialsMap)} must be encrypted</p>
     *
     * @param oAuth2Token a valid OAuth2 token to send along requests
     */
    public CustomerServiceREST(OAuth2Token oAuth2Token) {
        super(oAuth2Token);
    }

    /**
     * Creates a new instance of the customer service with encryption capability.
     * <p>When using this constructor credentials provided to
     * {@link #addBankzugaenge(LoginCredentialsMap)} will be encrypted on the fly
     * using the provided {@link CryptoService}.</p>
     *
     * @param oAuth2Token a valid OAuth2 token to send along requests
     * @param cryptoService Crypto service to use for credentials encryption
     */
    public CustomerServiceREST(OAuth2Token oAuth2Token, CryptoService cryptoService) {
        super(oAuth2Token);
        this.cryptoService = cryptoService;
    }

    public Response<Customer> getCustomer() {
        return createAuthenticatingHttpClient(getCustomerContext()).get(Customer.class);
    }

    public Response<BankzugangMap> getBankzugaenge() {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_BANKZUGAENGE);
        return createAuthenticatingHttpClient(bankzugaengeUrl).get(BankzugangMap.class);
    }

    public Response<BankzugangMap> getBankzugaengeTentative() {
        String path = "bankzugaenge?tentative=true";
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), path);
        return createAuthenticatingHttpClient(bankzugaengeUrl).get(BankzugangMap.class);
    }
    
    public Response<String> addBankzugaenge(LoginCredentialsMap loginCredentialsMap) {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_BANKZUGAENGE);

        LoginCredentialsMap loginCredentialsMapToUse = loginCredentialsMap;
        if (cryptoService != null) {
            loginCredentialsMapToUse = cryptoService.encryptLoginCredentialsMap(loginCredentialsMap);
        }

        return createAuthenticatingHttpClient(bankzugaengeUrl)
                .post(loginCredentialsMapToUse, String.class);
    }

    /**
     * Aktualisierung von Bankzugängen, selbst wenn schon vorhanden
     */
    public Response<String> addBankzugaengeRefresh(LoginCredentialsMap loginCredentialsMap) {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), "bankzugaenge?refresh=true");

        LoginCredentialsMap loginCredentialsMapToUse = loginCredentialsMap;
        if (cryptoService != null) {
            loginCredentialsMapToUse = cryptoService.encryptLoginCredentialsMap(loginCredentialsMap);
        }

        return createAuthenticatingHttpClient(bankzugaengeUrl)
                .post(loginCredentialsMapToUse, String.class);
    }

    public Response<String> disableBankzugaengeSync(String accountId) {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_BANKZUGANG_SYNC, accountId);

        Map<String, Object> request = Collections.singletonMap("sync", false);

        return createAuthenticatingHttpClient(bankzugaengeUrl)
                .put(request, String.class);
    }
    
    public Response<String> deleteBankzugaenge() {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_BANKZUGAENGE);
        return createAuthenticatingHttpClient(bankzugaengeUrl).delete();
    }

    public Response<String> deleteBankzugang(String accountId) {
        URL bankzugangUrl = buildUrl(getCustomerContext(), PATH_FMT_BANKZUGANG, accountId);
        return createAuthenticatingHttpClient(bankzugangUrl).delete();
    }

    public Response<Bankzugang> getBankzugang(String accountId) {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_BANKZUGANG, accountId);
        return createAuthenticatingHttpClient(bankzugaengeUrl).get(Bankzugang.class);
    }
    
    public Response<Bankzugang> getBankzugangTentative(String accountId) {
        String path = "bankzugaenge/%s?tentative=true";
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), path, accountId);
        return createAuthenticatingHttpClient(bankzugaengeUrl).get(Bankzugang.class);
    }

    public Response<Bankprodukt> getBankprodukt(String accountId, String productId) {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_PRODUKT, accountId, productId);
        return createAuthenticatingHttpClient(bankzugaengeUrl).get(Bankprodukt.class);
    }

    public Response<KontoumsatzList> getKontoumsaetze(String accountId, String productId) {
        URL kontoumsaetzeUrl = buildUrl(getCustomerContext(), PATH_FMT_KONTOUMSAETZE, accountId, productId);
        return createAuthenticatingHttpClient(kontoumsaetzeUrl).get(KontoumsatzList.class);
    }

    public Response<DepotpositionList> getDepotpositionen(String accountId, String productId) {
        URL depotpositionenUrl = buildUrl(getCustomerContext(), PATH_FMT_DEPOTPOSITIONEN, accountId, productId);
        return createAuthenticatingHttpClient(depotpositionenUrl).get(DepotpositionList.class);
    }

    public Response<UeberweisungErgebnis> createUeberweisung(String providerId, String productId,
            Ueberweisung ueberweisung) {
        if (cryptoService != null) {
            ueberweisung = new Ueberweisung(ueberweisung,
                    cryptoService.encryptCredentials(ueberweisung.getCredentials()));
        }

        URL ueberweisungUrl = buildUrl(getCustomerContext(), PATH_FMT_UEBERWEISUNG, providerId, productId);
        return createAuthenticatingHttpClient(ueberweisungUrl)
                .post(ueberweisung, UeberweisungErgebnis.class);
    }

    public Response<UeberweisungErgebnis> submitTextTan(String providerId, String productId,
            UUID transactionId, String tan) {
        URL submitTanUrl = buildUrl(getCustomerContext(), PATH_FMT_SUBMIT_TAN, providerId, productId, transactionId.toString());

        Map<String, String> submitTanBody = new HashMap<>();
        submitTanBody.put("tan", tan);

        return createAuthenticatingHttpClient(submitTanUrl)
                .put(submitTanBody, UeberweisungErgebnis.class);
    }

}
