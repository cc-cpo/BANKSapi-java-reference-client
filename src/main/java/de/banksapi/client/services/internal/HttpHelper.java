package de.banksapi.client.services.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class HttpHelper {

    private HttpHelper() {
    }

    private static URL buildUrlInternal(URL baseUrl, String path) {
        Objects.requireNonNull(baseUrl);
        if (path == null) {
            return baseUrl;
        }
        try {
            final boolean requireExtraSlash = !path.startsWith("/") && !baseUrl.getFile().endsWith("/");
            return new URL(baseUrl.getProtocol(), baseUrl.getHost(), baseUrl.getPort(),
                    String.format("%s%s%s", baseUrl.getFile(), requireExtraSlash ? "/" : "", path),
                    null);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Unable to build URL", e);
        }
    }

    public static URL buildUrl(URL baseUrl, String pathFmt, String... pathParts) {
        if (pathFmt == null) {
            return baseUrl;
        }
        final String path = String.format(pathFmt, (Object[]) pathParts);
        return buildUrlInternal(baseUrl, path);
    }
}
