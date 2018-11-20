package de.banksapi.client.services.internal;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.stream.Stream;

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

        final Object[] encodedParts = Stream.of(pathParts).map(HttpHelper::urlEncode).toArray();

        final String path = String.format(pathFmt,encodedParts);
        return buildUrlInternal(baseUrl, path);
    }

    private static String urlEncode(String raw) {
        Objects.requireNonNull(raw);
        try {
            return URLEncoder.encode(raw, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
