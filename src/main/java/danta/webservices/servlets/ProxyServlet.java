/*
 * Danta WebServices
 *
 * Copyright (C) 2017 Tikal Technologies, Inc. All rights reserved.
 *
 * Licensed under GNU Affero General Public License, Version v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/agpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied;
 * without even the implied warranty of MERCHANTABILITY.
 * See the License for more details.
 */

package danta.webservices.servlets;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.felix.scr.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static danta.Constants.BLANK;
import static danta.Constants.GET;
import static danta.Constants.TRUE;
import static danta.webservices.Constants.HTTP;
import static danta.webservices.Constants.HTTPMethod.*;
import static danta.webservices.Constants.HTTPS;

/**
 * @author  joshuaoransky
 * @since   2016-08-09
 */
@Component
public class ProxyServlet
        extends HttpServlet {

    private static final String NO_SSL = "noSSL";

    protected final Logger log = LoggerFactory.getLogger(getClass());


    private void doMethod(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        String noSSL = request.getParameter(NO_SSL);
        String scheme = (noSSL != null && noSSL.equalsIgnoreCase(TRUE)) ? HTTP : HTTPS;
        String requestURL = request.getRequestURI();
        String queryString = (request.getQueryString() == null) ? BLANK : request.getQueryString();
        String fullPath = request.getRequestURI() + (queryString.equals(BLANK) ? BLANK : "?" + queryString);
        Cookie[] cookies = request.getCookies();
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            headers.put(name, value);
        }

        HttpMethod httpMethod;

        switch (method) {
            case POST:
                httpMethod = new PostMethod();
                break;
            case DELETE:
            case PUT:
            case GET:
            default:
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doMethod(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doMethod(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doMethod(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doMethod(request, response);
    }
}
