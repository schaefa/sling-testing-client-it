/*
 * Copyright (c) Madplanet.com Inc. 2017
 */

package com.madplanet.sling.it;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClient;
import org.apache.sling.testing.clients.osgi.OsgiConsoleClient;
import org.apache.sling.testing.junit.rules.SlingInstanceRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class TCIT
{
    public static final Logger LOGGER = LoggerFactory.getLogger(TCIT.class.getName());

    @ClassRule
    public static SlingInstanceRule slingInstanceRule = new SlingInstanceRule();

    protected static SlingClient client;
    protected static OsgiConsoleClient osgiConsoleClient;

    @BeforeClass
    public static void setUpAll() throws ClientException, InterruptedException, IOException {
        LOGGER.info("Setup TCIT");
        client = slingInstanceRule.getAdminClient();
        osgiConsoleClient = new OsgiConsoleClient(client.getUrl(), client.getUser(), client.getPassword());
        LOGGER.info("DONE Setup TCIT");
    }

    @Test
    public void testCallWithParameterOnPath() throws Exception {
        String path = "http://localhost:8080/content/starter.json?a=b";
        URI uri = URI.create(path);
        LOGGER.info("URI: '{}'", uri);
        HttpGet get = new HttpGet(path);
        LOGGER.info("Http Get: '{}'", get);
        HttpGet get2 = new HttpGet(uri);
        LOGGER.info("Http Get (URI): '{}'", get2);
        URI uri2 = client.getUrl(path);
        LOGGER.info("URI2: '{}'", uri2);
        URI uri3 = client.getPath(path);
        LOGGER.info("URI3: '{}'", uri3);
        URIBuilder uriBuilder = new URIBuilder(uri2);
        URI uri4 = uriBuilder.build();
        LOGGER.info("URI4: '{}'", uri4);

        client.doGet(
            "/content/starter.json?a=b",
            null,
            200
        );
    }
}
