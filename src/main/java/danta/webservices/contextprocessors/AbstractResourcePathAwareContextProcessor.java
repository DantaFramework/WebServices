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

package danta.webservices.contextprocessors;

import danta.api.ExecutionContext;
import danta.api.exceptions.AcceptsException;
import danta.core.util.OSGiUtils;
import danta.webservices.Constants;
import danta.webservices.WebServiceExecutionContext;
import danta.webservices.uri.URIPattern;
import danta.webservices.uri.URIResolver;
import org.apache.felix.scr.annotations.Activate;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.NamespaceException;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static danta.Constants.LOW_PRIORITY;
import static danta.webservices.Constants.*;

/**
 * Abstract resource path aware context processor
 *
 * @author  joshuaoransky
 * @since   2016-07-28
 */
public abstract class AbstractResourcePathAwareContextProcessor
        extends AbstractWebServiceContextProcessor {

    private final List<URIPattern> patterns = new ArrayList<>();

    private static final List<String> METHODS = Arrays.asList(
            Constants.HTTPMethod.GET
    );

    @Override
    public final boolean accepts(ExecutionContext executionContext)
            throws AcceptsException {
        if (super.accepts(executionContext)) {
            WebServiceExecutionContext wsExecutionContext = (WebServiceExecutionContext) executionContext;
            String requestMethod = (String) wsExecutionContext.get(REQUEST_METHOD);
            if (methods().contains(requestMethod)) {
                String resourcePath = (String) wsExecutionContext.get(REQUEST_FULLPATH);
                URIResolver resolver = new URIResolver(resourcePath);
                Collection<URIPattern> matchingPatterns = resolver.findAll(patterns);
                if (!matchingPatterns.isEmpty()) {
                    wsExecutionContext.put(URI_RESOLVER, resolver);
                    wsExecutionContext.put(MATCHED_URI_PATTERNS, matchingPatterns);
                    return continueAccepts(executionContext);
                }
            }
        }
        return false;
    }

    protected boolean continueAccepts(ExecutionContext executionContext)
            throws AcceptsException {
        return true;
    }

    protected abstract List<String> pathPatterns();

    protected List<String> methods() {
        return METHODS;
    }

    @Override
    public int priority() {
        return LOW_PRIORITY;
    }

    protected ComponentContext componentContext;

    @Activate
    protected void activate(ComponentContext componentContext)
            throws ServletException, NamespaceException {
        this.componentContext = componentContext;
        OSGiUtils.activate(this, componentContext);
        for (String pattern : pathPatterns()) {
            patterns.add(new URIPattern(pattern));
        }
    }
}
