package com.blog.app.domain;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public interface UserHandler extends HttpHandler {
    void handle(HttpExchange exchange)  throws IOException ;
}