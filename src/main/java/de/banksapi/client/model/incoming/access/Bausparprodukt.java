package de.banksapi.client.model.incoming.access;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.banksapi.client.model.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bausparprodukt extends Bankprodukt {

    String vertragsnummer;

    Double rating;

    Double vertragssumme;

    String vertragstyp;

    Boolean sparzustand;

    String vertragsstatus;

    Double sparzinssatz;

    Double schuldzinssatz;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime vertragsDatum;

    Double sparfortschritt;

    public String getVertragsnummer() {
        return vertragsnummer;
    }

    public Double getRating() {
        return rating;
    }

    public Double getVertragssumme() {
        return vertragssumme;
    }

    public String getVertragstyp() {
        return vertragstyp;
    }

    public Boolean getSparzustand() {
        return sparzustand;
    }

    public String getVertragsstatus() {
        return vertragsstatus;
    }

    public Double getSparzinssatz() {
        return sparzinssatz;
    }

    public Double getSchuldzinssatz() {
        return schuldzinssatz;
    }

    public LocalDateTime getVertragsDatum() {
        return vertragsDatum;
    }

    public Double getSparfortschritt() {
        return sparfortschritt;
    }

}
