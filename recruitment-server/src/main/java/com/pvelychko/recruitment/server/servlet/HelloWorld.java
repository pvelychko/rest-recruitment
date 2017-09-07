package com.pvelychko.recruitment.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet {
    private volatile boolean lastWasPut = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        if(this.lastWasPut) {
            resp.getWriter()
                    .print("{\"Andrea Räksmörgås\": {\"name\": {\"given\": \"Andrea\",\"family\": \"Räksmörgås\"},\"gender\": \"female\",\"email\": [\"a.r@foo.aol.com\"],\"homepage\": \"http://ar.foo.webb.se\"}}");

        }
        else {
            resp.getWriter()
                    .print("{\"Anders Svensson\": {\"name\": {\"given\": \"Anders\",\"family\": \"Svensson\"},\"gender\": \"male\",\"email\": [\"a.s@foo.yahoo.se\"],\"homepage\": \"http://as.foo.hem.se\"}}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.lastWasPut = false;
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.addHeader("Location", "http://127.0.0.1:8080/person/123");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.lastWasPut = true;
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
