package de.banksapi.client;

import de.banksapi.client.services.internal.HttpHelper;
import de.banksapi.client.services.internal.Preconditions;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpHelperTest {

    @Test
    public void concatWithContext() throws Exception {
        URL url = HttpHelper.buildUrl(new URL("https://proxy.example.com/mycontext"), "customer/v2/");
        Assert.assertEquals("https://proxy.example.com/mycontext/customer/v2/", url.toString());
    }

    @Test
    public void concatWithLeadingSlash() throws Exception {
        URL url = HttpHelper.buildUrl(new URL("https://proxy.example.com/mycontext"), "/customer/v2/");
        Assert.assertEquals("https://proxy.example.com/mycontext/customer/v2/", url.toString());
    }

    @Test
    public void concatWithNoAppend() throws Exception {
        URL url = HttpHelper.buildUrl(new URL("https://proxy.example.com/mycontext"), null);
        Assert.assertEquals("https://proxy.example.com/mycontext", url.toString());
    }


    @Test
    public void concatWithTrailingSlashInContext() throws Exception {
        URL url = HttpHelper.buildUrl(new URL("https://proxy.example.com/mycontext/"), "customer/v2/");
        Assert.assertEquals("https://proxy.example.com/mycontext/customer/v2/", url.toString());
    }


    @Test
    public void concatWithParams() throws Exception {
        URL url = HttpHelper.buildUrl(new URL("https://proxy.example.com/mycontext"), "/customer/v2/bankzugaenge/%s", "my-param");
        Assert.assertEquals("https://proxy.example.com/mycontext/customer/v2/bankzugaenge/my-param", url.toString());
    }

    @Test
    public void concatWithParamsSpecialChards() throws Exception {
        URL url = HttpHelper.buildUrl(new URL("https://proxy.example.com/mycontext"), "/customer/v2/bankzugaenge/1234-4321/%s/kontoumsaetze", "Sparverträge Nr. 6767 999-99+2");
        Assert.assertEquals("https://proxy.example.com/mycontext/customer/v2/bankzugaenge/1234-4321/Sparvertr%C3%A4ge+Nr.+6767+999-99%2B2/kontoumsaetze", url.toString());
    }

}
