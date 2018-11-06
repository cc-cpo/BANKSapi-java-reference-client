package de.banksapi.client.services;

public enum PropertyNamingStrategy {
    SNAKE_CASE,
    LOWER_CAMEL_CASE;

    public com.fasterxml.jackson.databind.PropertyNamingStrategy toJacksonStrategy() {
        switch (this) {
            case SNAKE_CASE:
                return com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;
            case LOWER_CAMEL_CASE:
                return com.fasterxml.jackson.databind.PropertyNamingStrategy.LOWER_CAMEL_CASE;
            default:
                return null;
        }
    }
}
