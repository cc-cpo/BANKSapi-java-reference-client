package de.banksapi.client.services;

import java.util.UUID;

public interface ICorrelationIdStrategy {

    UUID getCorrelationId();

}
