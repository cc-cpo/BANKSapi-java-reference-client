package de.banksapi.client.services.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.banksapi.client.services.IHTTPClientUnconfigured;
import de.banksapi.client.services.PropertyNamingStrategy;
import de.banksapi.client.services.Response;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static de.banksapi.client.services.internal.StringUtil.isBlank;

/**
 * This class provides basic HTTP communication functions via {@link HttpsURLConnection} and acts
 * as a facade to ease the integration of a different HTTP client such as OkHttp, Apache CXF, Apache
 * HttpComponents and so on.
 * <p>This client is stateful and you should use one {@link SimpleHttpClient} instance per request.</p>
 */
public class SimpleHttpClient implements IHTTPClientUnconfigured {

    private URL url;

    private HttpsURLConnection httpsURLConnection;

    private ObjectMapper objectMapper;

    private static final int connectTimeout = 30000; // 30 seconds in ms
    private static final int readTimeout = 300000; // five minutes in ms

    public SimpleHttpClient(URL url) {
        try {
            this.url = url;
            httpsURLConnection = (HttpsURLConnection) this.url.openConnection();
            httpsURLConnection.setConnectTimeout(connectTimeout);
            httpsURLConnection.setReadTimeout(readTimeout);
            objectMapper = new ObjectMapper();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid URL '" + url + "'", e);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to setup HTTP connection to '" + url + "'", e);
        }
    }

    @Override
    public URL getRequestUrl() {
        return url;
    }

    public void setObjectMapperPropertyNamingStrategy(PropertyNamingStrategy namingStrategy) {
        this.objectMapper.setPropertyNamingStrategy(namingStrategy.toJacksonStrategy());
    }

    public IHTTPClientUnconfigured setHeader(String key, String value) {
        httpsURLConnection.setRequestProperty(key, value);
        return this;
    }

    public <T> Response<T> postForm(String postData, Class<T> responseClass) {
        try {
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoOutput(true);
            try (DataOutputStream out = new DataOutputStream(httpsURLConnection.getOutputStream())) {
                out.write(postData.getBytes());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to setup POST request to '" + url + "'", e);
        }

        return performRequest(responseClass);
    }

    public <U, T> Response<T> post(U postData, Class<T> responseClass) {
        try {
            return postForm(objectMapper.writeValueAsString(postData), responseClass);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("Unable to serialize object to JSON", e);
        }
    }

    public <T> Response<T> post(Class<T> responseClass) {
        try {
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoOutput(true);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to setup POST request to '" + url + "'", e);
        }

        return performRequest(responseClass);
    }

    public <T> Response<T> put(String putData, Class<T> responseClass) {
        try {
            httpsURLConnection.setRequestMethod("PUT");
            httpsURLConnection.setDoOutput(true);
            try (DataOutputStream out = new DataOutputStream(httpsURLConnection.getOutputStream())) {
                out.write(putData.getBytes());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to setup POST request to '" + url + "'", e);
        }

        return performRequest(responseClass);
    }

    public <U, T> Response<T> put(U putData, Class<T> responseClass) {
        try {
            return put(objectMapper.writeValueAsString(putData), responseClass);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("Unable to serialize object to JSON", e);
        }
    }

    public <T> Response<T> put(Class<T> responseClass) {
        try {
            httpsURLConnection.setRequestMethod("PUT");
            httpsURLConnection.setDoOutput(true);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to setup PUT request to '" + url + "'", e);
        }

        return performRequest(responseClass);
    }

    public Response<String> delete() {
        try {
            httpsURLConnection.setRequestMethod("DELETE");
            httpsURLConnection.setDoOutput(true);
        } catch (ProtocolException e) {
            throw new IllegalStateException("Unable to setup DELETE request to '" + url + "'", e);
        }

        return performRequest(String.class);
    }

    public <T> Response<T> get(Class<T> responseClass) {
        try {
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setDoOutput(true);
        } catch (ProtocolException e) {
            throw new IllegalStateException("Unable to setup GET request to '" + url + "'", e);
        }

        return performRequest(responseClass);
    }

    private <T> Response<T> performRequest(Class<T> responseClass) {
        final ResponseImpl<T> response = new ResponseImpl<>();

        try {
            response.httpCode = httpsURLConnection.getResponseCode();

            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStream errorStream = httpsURLConnection.getErrorStream();
            T object = null;
            if (responseClass != null && inputStream.available() > 0) {
                String input = readStream(inputStream);
                object = objectMapper.readValue(input, responseClass);
            } else if (errorStream != null && errorStream.available() > 0) {
                response.error = readStream(errorStream);
            }
            response.data = object;

            String location = httpsURLConnection.getHeaderField("location");
            if (!isBlank(location)) {
                response.location = location;
            }
        } catch (Exception ex) {
            String serverMessage = "";
            try {
                serverMessage = "; server message: " + readStream(httpsURLConnection.getErrorStream());
            } catch (IOException ignored) {}
            response.error = ex.getMessage() + serverMessage;
        }

        return response;
    }

    private static String readStream(InputStream stream) throws IOException {
        if (stream == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
        }

        return builder.toString();
    }

}
