package de.banksapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.banksapi.client.model.incoming.access.Bankprodukt;
import de.banksapi.client.model.incoming.access.Bankzugang;
import de.banksapi.client.model.incoming.access.Bausparprodukt;
import de.banksapi.client.model.incoming.access.Kategorie;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class JacksonInheritanceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void castSubtypeBausparprodukt() throws Exception {

        String json = "{\n" +
                "  \"status\": \"VOLLSTAENDIG\",\n" +
                "  \"aktualisierungszeitpunkt\": \"2016-10-28 14:33:02\",\n" +
                "  \"timeout\": \"2016-10-28 14:53:02\",\n" +
                "  \"tanMedien\": [\n" +
                "    {\n" +
                "      \"gueltigVon\": \"2016-10-28 14:33:03\",\n" +
                "      \"gueltigBis\": \"2016-10-28 14:33:03\",\n" +
                "      \"name\": \"Mobil\",\n" +
                "      \"medienklasse\": \"MOBIL\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sicherheitsverfahren\": [\n" +
                "    {\n" +
                "      \"kodierung\": 1,\n" +
                "      \"name\": \"Mock-TAN\",\n" +
                "      \"hinweis\": \"Mock-TAN\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"aktivesSicherheitsverfahren\": {\n" +
                "    \"kodierung\": 1,\n" +
                "    \"name\": \"Mock-TAN\",\n" +
                "    \"hinweis\": \"Mock-TAN\"\n" +
                "  },\n" +
                "  \"bankprodukte\": [\n" +
                "    {\n" +
                "      \"id\": \"DE00123456789012345679\",\n" +
                "      \"bezeichnung\": \"Tagesgeldkonto\",\n" +
                "      \"kategorie\": \"TAGESGELDKONTO\",\n" +
                "      \"aktualisierungszeitpunkt\": \"2016-10-28 14:33:03\",\n" +
                "      \"saldo\": 7365.56,\n" +
                "      \"saldoDatum\": \"2016-01-18 00:00:00\",\n" +
                "      \"waehrung\": \"EUR\",\n" +
                "      \"kontonummer\": \"9012345679\",\n" +
                "      \"iban\": \"DE00123456789012345679\",\n" +
                "      \"bic\": \"XXX12345678\",\n" +
                "      \"blz\": \"12345678\",\n" +
                "      \"inhaber\": \"Fritz Testmüller\",\n" +
                "      \"verfuegungsrahmen\": 7365.56,\n" +
                "      \"verfuegterBetrag\": 0.0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"4049181-037-LBS-V35\",\n" +
                "        \"bezeichnung\": \"LBS-V35\",\n" +
                "        \"kategorie\": \"BAUSPARVERTRAG\",\n" +
                "        \"saldo\": 30.9,\n" +
                "        \"aktualisierungszeitpunkt\": \"2018-11-21 18:22:42\",\n" +
                "        \"saldoDatum\": \"2018-11-21 18:22:39\",\n" +
                "        \"waehrung\": \"EUR\",\n" +
                "        \"kontonummer\": \"4049181-037-LBS-V35\",\n" +
                "        \"bic\": \"BYLADEMM\",\n" +
                "        \"blz\": \"70050000\",\n" +
                "        \"kreditinstitut\": \"LBS Bayern\",\n" +
                "        \"inhaber\": \"Manuel Wanner-Behr\",\n" +
                "        \"relations\": [\n" +
                "            {\n" +
                "                \"rel\": \"self\",\n" +
                "                \"href\": \"https://test.banksapi.io/customer/v2/bankzugaenge/deadbeef-cafe-1111-0000-0167374b7c94/4049181-037-LBS-V35\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rel\": \"get_kontoumsaetze\",\n" +
                "                \"href\": \"https://test.banksapi.io/customer/v2/bankzugaenge/deadbeef-cafe-1111-0000-0167374b7c94/4049181-037-LBS-V35/kontoumsaetze\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"messages\": [],\n" +
                "        \"vertragsnummer\": \"Bausparvertrag4049181/037 LBS-V35\",\n" +
                "        \"rating\": 1.71,\n" +
                "        \"vertragssumme\": 20000,\n" +
                "        \"vertragstyp\": \"LBS-V35\",\n" +
                "        \"sparzustand\": true,\n" +
                "        \"vertragsstatus\": \"in Ansparung\",\n" +
                "        \"sparzinssatz\": 1.5,\n" +
                "        \"vertragsDatum\": \"2005-04-19 00:00:00\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

        Bankzugang bankzugang = objectMapper.readValue(json, Bankzugang.class);

        LinkedList<Bankprodukt> bankprodukts = new LinkedList<>(bankzugang.getBankprodukte());

        Bankprodukt otherProdukt = bankprodukts.get(0);
        Bausparprodukt bausparprodukt = (Bausparprodukt) bankprodukts.get(1);
//        System.out.println(objectMapper.writeValueAsString(produkt));

        Assert.assertEquals(Bankprodukt.class, otherProdukt.getClass());
        Assert.assertEquals(Kategorie.TAGESGELDKONTO, otherProdukt.getKategorie());
        Assert.assertEquals("Tagesgeldkonto", otherProdukt.getBezeichnung());

        Assert.assertEquals(Bausparprodukt.class, bausparprodukt.getClass());
        Assert.assertEquals("Bausparvertrag4049181/037 LBS-V35", bausparprodukt.getVertragsnummer());
        Assert.assertEquals(1.71, bausparprodukt.getRating(), 0.05);
        Assert.assertEquals(20_000.00, bausparprodukt.getVertragssumme(), 0.05);
        Assert.assertEquals("LBS-V35", bausparprodukt.getVertragstyp());
        Assert.assertEquals(true, bausparprodukt.getSparzustand());
        Assert.assertEquals("in Ansparung", bausparprodukt.getVertragsstatus());
        Assert.assertEquals(1.5, bausparprodukt.getSparzinssatz(), 0.05);
        Assert.assertEquals(null, bausparprodukt.getSchuldzinssatz());
        Assert.assertEquals("2005-04-19T00:00", bausparprodukt.getVertragsDatum().toString());
        Assert.assertEquals(null, bausparprodukt.getSparfortschritt());

    }
}
