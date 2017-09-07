package com.pvelychko.recruitment.verification;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public abstract class TestUtil {
    public final ClientResponse fetch(final URI uri) {
        final WebResource resource = createClient();
        final ClientResponse response = resource.uri(uri).get(ClientResponse.class);
        final Family family = Status.fromStatusCode(response.getStatus()).getFamily();
        assertTrue("GET not successful: " + response.getStatus() + " for " + uri, Family.SUCCESSFUL == family);
        return response;
    }

    public final void compare(final ClientResponse response, final String compareAgainst) {
        final InputStream stream = response.getEntityInputStream();
        final JsonNode expected = loadJson(load(compareAgainst), "ERROR IN TEST: Failed to parse expected JSON");
        final JsonNode actual = loadJson(stream, "Service returned invalid JSON");
        if(!expected.equals(actual)) {
            fail("Output did not match, expected:\n" + expected.toString() + "\nbut was:\n" + actual);
        }
    }

    public static final JsonNode loadJson(final InputStream stream, final String failureMessage) {
        try {
            return new ObjectMapper().readValue(stream, JsonNode.class);
        } catch(final Exception e) {
            fail(failureMessage + ": " + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public final InputStream load(final String name) {
        final String resource = getClass().getPackage().getName().replace('.', '/') + '/' + name;
        final InputStream stream = getClass().getClassLoader().getResourceAsStream(resource);
        assertNotNull("Failed to load resource: " + resource, stream);
        return stream;
    }

    public final URI doCreate(final String file) {
        final WebResource resource = createClient();
        final ClientResponse response = resource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, load(file));
        final Status status = Status.fromStatusCode(response.getStatus());
        assertTrue("POST was unsuccesful: " + response.getStatus() + " " + status, status.getFamily() == Family.SUCCESSFUL);
        final MultivaluedMap<String, String> headers = response.getHeaders();
        assertTrue("Missing location header", headers.containsKey("Location"));
        final String uri = headers.getFirst("Location");
        try {
            return new URI(uri);
        } catch(final URISyntaxException e) {
            fail("Invalid URI for created resource: " + uri);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public final void doUpdate(final URI uri, final String file) {
        final WebResource resource = createClient();
        final ClientResponse response = resource.uri(uri).type(MediaType.APPLICATION_XML).put(ClientResponse.class, load(file));
        final Status status = Status.fromStatusCode(response.getStatus());
        System.out.println("PUT served as: " + status);
        assertNotNull("PUT not implemented?", status);
        assertTrue("PUT not succesful", status.getFamily() == Family.SUCCESSFUL);
    }

    public static final WebResource createClient() {
        final Client client = Client.create();
        return client.resource("http://127.0.0.1:8080/person");
    }
}