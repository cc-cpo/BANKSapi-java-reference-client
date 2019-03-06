package de.banksapi.client.services;

import de.banksapi.client.crypto.CryptoService;
import de.banksapi.client.model.incoming.access.*;
import de.banksapi.client.model.incoming.oauth2.OAuth2Token;
import de.banksapi.client.model.outgoing.access.LoginCredentialsMap;
import de.banksapi.client.model.outgoing.access.Ueberweisung;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static de.banksapi.client.services.internal.HttpHelper.buildUrl;

/**
 * This service interfaces with the Banks/Connect Customer API. This API provides all relevant
 * customer information, such as banking accounts, products and turnovers.
 *
 * <p>This service uses the APIs in the HATEOAS style.</p>
 *
 * @see <a href="https://docs.banksapi.de/customer.html">Banks/Connect Customer API</a>
 */
public class CustomerServiceHATEOAS extends CustomerServiceBase {

    private CryptoService cryptoService;

    /**
     * Creates a new instance of the customer service without encryption capability.
     *
     * <p>When using this constructor credentials provided to
     * {@link #addBankzugaenge(Customer, LoginCredentialsMap)} must be encrypted</p>
     *
     * @param oAuth2Token a valid OAuth2 token to send along requests
     */
    public CustomerServiceHATEOAS(OAuth2Token oAuth2Token) {
        super(oAuth2Token);
    }

    /**
     * Creates a new instance of the customer service with encryption capability.
     *
     * <p>When using this constructor credentials provided to
     * {@link #addBankzugaenge(Customer, LoginCredentialsMap)} will be encrypted on the fly
     * using the provided {@link CryptoService}.</p>
     *
     * @param oAuth2Token a valid OAuth2 token to send along requests
     * @param cryptoService Crypto service to use for credentials encryption
     */
    public CustomerServiceHATEOAS(OAuth2Token oAuth2Token, CryptoService cryptoService) {
        super(oAuth2Token);
        this.cryptoService = cryptoService;
    }

    public Response<Customer> getCustomer() {
        return createAuthenticatedHttpClient(getCustomerContext()).get(Customer.class);
    }

    public Response<BankzugangMap> getBankzugaenge(Customer customer) {
        return createAuthenticatedHttpClient(customer.getUrlForRelation("get_bankzugaenge"))
                .get(BankzugangMap.class);
    }

    public Response<String> addBankzugaenge(Customer customer, LoginCredentialsMap loginCredentialsMap) {
        LoginCredentialsMap loginCredentialsMapToUse = loginCredentialsMap;
        if (cryptoService != null) {
            loginCredentialsMapToUse = cryptoService.encryptLoginCredentialsMap(loginCredentialsMap);
        }

        return createAuthenticatedHttpClient(customer.getUrlForRelation("add_bankzugaenge"))
                .post(loginCredentialsMapToUse, String.class);
    }

    public Response<String> deleteBankzugaenge(Customer customer) {
        return createAuthenticatedHttpClient(customer.getUrlForRelation("delete_bankzugaenge"))
                .delete();
    }

    public Response<String> deleteBankzugang(Bankzugang account) {
        return createAuthenticatedHttpClient(account.getUrlForRelation("delete_bankzugang"))
                .delete();
    }

    public Response<Bankzugang> getBankzugang(String accountId) {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_BANKZUGANG, accountId);
        return createAuthenticatedHttpClient(bankzugaengeUrl).get(Bankzugang.class);
    }

    public Response<Bankprodukt> getBankprodukt(String accountId, String productId) {
        URL bankzugaengeUrl = buildUrl(getCustomerContext(), PATH_FMT_PRODUKT, accountId, productId);
        return createAuthenticatedHttpClient(bankzugaengeUrl).get(Bankprodukt.class);
    }

    public Response<KontoumsatzList> getKontoumsaetze(Bankprodukt bankprodukt) {
        return createAuthenticatedHttpClient(bankprodukt.getUrlForRelation("get_kontoumsaetze"))
                .get(KontoumsatzList.class);
    }

    public Response<DepotpositionList> getDepotpositionen(Bankprodukt bankprodukt) {
        return createAuthenticatedHttpClient(bankprodukt.getUrlForRelation("get_depotpositionen"))
                .get(DepotpositionList.class);
    }

    public Response<UeberweisungErgebnis> createUeberweisung(Bankprodukt bankprodukt,
            Ueberweisung ueberweisung) {
        if (cryptoService != null) {
            ueberweisung = new Ueberweisung(ueberweisung,
                    cryptoService.encryptCredentials(ueberweisung.getCredentials()));
        }

        return createAuthenticatedHttpClient(bankprodukt.getUrlForRelation("start_ueberweisung"))
                .post(ueberweisung, UeberweisungErgebnis.class);
    }

    public Response<UeberweisungErgebnis> submitTextTan(UeberweisungErgebnis ueberweisungErgebnis,
            String tan) {
        URL submitTanUrl = ueberweisungErgebnis.getUrlForRelation("submit_text_tan");

        Map<String, String> submitTanBody = new HashMap<>();
        submitTanBody.put("tan", tan);

        return createAuthenticatedHttpClient(submitTanUrl)
                .put(submitTanBody, UeberweisungErgebnis.class);
    }

}
