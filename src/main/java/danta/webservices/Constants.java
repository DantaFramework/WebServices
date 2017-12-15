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

package danta.webservices;

/**
 * @author  joshuaoransky
 * @since   2016-07-25
 */
public class Constants {

    public static final String EXECUTION_CONTEXT = "executionContext";
    public static final String CONTENT_MODEL = "contentModel";

    public static final String SERVICE_PATH = "danta.service.path";

    public static final String REQUEST_METHOD = "requestMethod";
    public static final String REQUEST_URL = "requestURL";
    public static final String REQUEST_PATH = "requestPath";
    public static final String REQUEST_QUERYSTRING = "requestQueryString";
    public static final String REQUEST_FULLPATH = "requestFullPath";
    public static final String REQUEST_BODY = "requestBody";
    public static final String REQUEST_BODY_JSON = "requestBodyJson";
    public static final String REQUEST_INSTANCE = "requestInstance";

    public static final String URI_RESOLVER = "uriResolver";
    public static final String MATCHED_URI_PATTERNS = "matchedURIPatterns";

    public static final String HTTPS = "https";
    public static final String HTTP = "http";

    public static final class HTTPMethod {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";

    }
}
