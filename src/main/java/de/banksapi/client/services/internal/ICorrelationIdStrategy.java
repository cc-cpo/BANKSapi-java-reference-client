package de.banksapi.client.services.internal;

import java.util.UUID;

public interface ICorrelationIdStrategy {

    UUID getCorrelationId();

}
