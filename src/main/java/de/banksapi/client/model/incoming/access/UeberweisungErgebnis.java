package de.banksapi.client.model.incoming.access;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.banksapi.client.model.LocalDateTimeDeserializer;
import de.banksapi.client.model.incoming.Relation;
import de.banksapi.client.model.incoming.Relations;

import java.time.LocalDateTime;
import java.util.Collection;

public class UeberweisungErgebnis implements Relations {

    private Collection<Message> messages;
    private Collection<Relation> relations;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeout;

    private Challenge challenge;

    public Collection<Message> getMessages() {
        return messages;
    }

    @Override
    public Collection<Relation> getRelations() {
        return relations;
    }

    public LocalDateTime getTimeout() {
        return timeout;
    }

    public Challenge getChallenge() {
        return challenge;
    }

}
