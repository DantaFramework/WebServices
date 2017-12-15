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

import danta.api.ContextProcessor;
import danta.api.ExecutionContext;
import danta.api.exceptions.AcceptsException;
import danta.webservices.WebServicesContentModel;
import danta.webservices.WebServiceExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static danta.webservices.Constants.REQUEST_URL;

/**
 * Abstract Web Service Context Processor
 *
 * @author  joshuaoransky
 * @since   2016-07-27
 */
public abstract class AbstractWebServiceContextProcessor
        implements ContextProcessor<WebServicesContentModel> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public boolean accepts(ExecutionContext executionContext)
            throws AcceptsException {
        WebServiceExecutionContext wsExecutionContext = (WebServiceExecutionContext) executionContext;
        return wsExecutionContext.has(REQUEST_URL);
    }
}
