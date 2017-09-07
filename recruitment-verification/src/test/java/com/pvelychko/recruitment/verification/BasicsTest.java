package com.pvelychko.recruitment.verification;

import org.junit.Test;

import java.net.URI;

public final class BasicsTest extends TestUtil {
    @Test
    public void SKAPA() {
        doCreate("anders.1.xml");
    }

    @Test
    public void HAMTA() {
        final URI uri = doCreate("anders.1.xml");
        compare(fetch(uri), "anders.1.json");
    }

    @Test
    public void UPPDATERA() {
        final URI uri = doCreate("anders.1.xml");
        compare(fetch(uri), "anders.1.json");
        doUpdate(uri, "anders.2.xml");
        compare(fetch(uri), "anders.2.json");
    }
}