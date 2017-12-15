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

import danta.api.ContextProcessorEngine;
import danta.core.util.OSGiUtils;
import danta.webservices.WebServiceExecutionContext;
import danta.webservices.WebServicesContentModel;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.felix.scr.annotations.*;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static danta.Constants.*;
import static danta.webservices.Constants.*;

/**
 * @author  joshuaoransky
 * @since   2016-07-27
 */
@Component(componentAbstract = true, immediate = true)
public class AbstractDantaServlet
        extends HttpServlet {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Reference
    private HttpService httpService;

    protected ComponentContext componentContext;

    private String servletPath;

    @Activate
    protected final void activate(ComponentContext componentContext)
            throws ServletException, NamespaceException {
        this.componentContext = componentContext;
        OSGiUtils.activate(this, componentContext);
        servletPath = (String) componentContext.getProperties().get(SERVICE_PATH);
        httpService.registerServlet(servletPath, this, null, null);
        log.info("{} registered at {}", this.getClass().getName(), servletPath);
    }

    private void processRequest(HttpServletRequest request)
            throws ServletException, IOException {
        WebServiceExecutionContext executionContext = new WebServiceExecutionContext();
        WebServicesContentModel contentModel = new WebServicesContentModel();


        if (!ServletFileUpload.isMultipartContent(request)) {
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            if (!requestBody.isEmpty() && JSON_MIME_TYPE.equalsIgnoreCase(request.getContentType())) {
                if (JSONValue.isValidJson(requestBody)) {
                    try {
                        JSONObject requestObj = (JSONObject) JSONValue.parse(requestBody);
                        JSONObject executionContextObj = (JSONObject) requestObj.get(EXECUTION_CONTEXT);
                        executionContext.merge(executionContextObj);

                        // must be set after executionContext is overwritten
                        executionContext.put(REQUEST_BODY_JSON, requestObj);
                        request.setAttribute(REQUEST_BODY_JSON, requestObj);

                        JSONObject contentModelObj = (JSONObject) requestObj.get(CONTENT_MODEL);
                        contentModel.merge(contentModelObj);

                    } catch (Exception ew) {
                        log.error(ew.getMessage(), ew);
                    }
                }
                executionContext.put(REQUEST_BODY, requestBody);
                request.setAttribute(REQUEST_BODY, requestBody);
            }
        }

        executionContext.put(REQUEST_INSTANCE, request);
        executionContext.put(REQUEST_METHOD, request.getMethod());
        executionContext.put(REQUEST_URL, request.getRequestURL().toString());
        executionContext.put(REQUEST_PATH, request.getRequestURI());
        String queryString = (request.getQueryString() == null) ? BLANK : request.getQueryString();
        executionContext.put(REQUEST_QUERYSTRING, queryString);
        executionContext.put(REQUEST_FULLPATH, request.getRequestURI() + (queryString.equals(BLANK) ? BLANK : "?" + queryString));

        request.setAttribute(EXECUTION_CONTEXT, executionContext);
        request.setAttribute(CONTENT_MODEL, contentModel);
    }

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
    private ContextProcessorEngine contextProcessorEngine;

    protected void onMethod(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            WebServiceExecutionContext executionContext = (WebServiceExecutionContext) request.getAttribute(EXECUTION_CONTEXT);
            WebServicesContentModel contentModel = (WebServicesContentModel) request.getAttribute(CONTENT_MODEL);

            List<String> currentProcessorChain = contextProcessorEngine.execute(executionContext, contentModel);

            Map<String, Object> statisticsMap = new HashMap<>();
            statisticsMap.put(PROCESSORS, currentProcessorChain);
            contentModel.set(STATISTICS_KEY, statisticsMap); //Add to ContentModel for later inspection by Components

            response.getWriter().print(contentModel.getJSON().toString());

        } catch (Exception ew) {
            log.error(ew.getMessage(), ew);
        }
    }

    protected void onGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleMethodNotImplemented(request, response);
    }

    protected void onPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleMethodNotImplemented(request, response);
    }

    protected void onPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleMethodNotImplemented(request, response);
    }

    protected void onDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleMethodNotImplemented(request, response);
    }

    protected void onHead(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleMethodNotImplemented(request, response);
    }

    protected void onOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleMethodNotImplemented(request, response);
    }

    protected void onTrace(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleMethodNotImplemented(request, response);
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        onGet(request, response);
    }

    @Override
    protected final void doHead(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        onHead(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        onPost(request, response);
    }

    @Override
    protected final void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        onPut(request, response);
    }

    @Override
    protected final void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        onDelete(request, response);
    }

    @Override
    protected final void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        onOptions(request, response);
    }

    @Override
    protected final void doTrace(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        onTrace(request, response);
    }

    protected void handleMethodNotImplemented(HttpServletRequest request,
                                              HttpServletResponse response)
            throws IOException {
        String protocol = request.getProtocol();
        String msg = "Method " + request.getMethod() + " not supported";

        if (protocol.endsWith("1.1")) {

            // for HTTP/1.1 use 405 Method Not Allowed
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);

        } else {

            // otherwise use 400 Bad Request
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);

        }
    }
}
