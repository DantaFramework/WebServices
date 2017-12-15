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

import danta.api.ExecutionContext;
import net.minidev.json.JSONObject;

/**
 * @author  joshuaoransky
 * @since   2016-04-29
 */
public class WebServiceExecutionContext
        implements ExecutionContext {

    private final JSONObject contextObj;

    public WebServiceExecutionContext() {
        this(null);
    }

    public WebServiceExecutionContext(JSONObject obj) {
        this.contextObj = obj != null ? obj : new JSONObject();
    }

    @Override
    public Object get(String key) {
        return contextObj.get(key);
    }

    public void put(final String key, final Object value) {
        contextObj.put(key, value);
    }

    public void merge(final JSONObject obj) {
        contextObj.merge(obj);
    }

    public boolean has(String key) {
        return get(key) != null;
    }

    public JSONObject getJSON() {
        return contextObj;
    }

    @Override
    public String toString() {
        return getJSON().toJSONString();
    }
}
