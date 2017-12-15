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

import danta.api.ContentModel;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static danta.Constants.BLANK;

/**
 * @author  joshuaoransky
 * @since   2016-07-25
 */
public class WebServicesContentModel
        implements ContentModel {

    private final JSONObject modelObj;

    public WebServicesContentModel() {
        this(null);
    }

    public WebServicesContentModel(JSONObject model) {
        this.modelObj = model != null ? model : new JSONObject();
    }

    @Override
    public synchronized ContentModel set(String name, Object value) { //TODO: This will need more development
        JSONObject currentModelObj = modelObj;
        JSONObject parentModelObj = null;
        String currentName = name;
        if (name.contains(".")) {
            List<String> names = new ArrayList(Arrays.asList(name.split("\\.")));
            currentName = names.remove(names.size() - 1);
            for (String n : names) {
                parentModelObj = currentModelObj;
                currentModelObj = (JSONObject) currentModelObj.get(n);
                if (currentModelObj == null) {
                    currentModelObj = new JSONObject();
                    parentModelObj.put(n, currentModelObj);
                }
            }
        }
        currentModelObj.put(currentName, value);
        return this;
    }

    public void merge(final JSONObject obj) {
        modelObj.merge(obj);
    }

    @Override
    public String getAsString(String name) {
        Object nameObj = get(name);
        return (nameObj != null) ? nameObj.toString() : BLANK;
    }

    @Override
    public Object get(String name) {
        JSONObject currentModelObj = modelObj;
        String currentName = name;
        if (name.contains(".")) {
            List<String> names = new ArrayList<>(Arrays.asList(name.split("\\.")));
            currentName = names.remove(names.size() - 1);
            for (String n : names) {
                currentModelObj = (JSONObject) currentModelObj.get(n);
                if (currentModelObj == null)
                    return null;
            }
        }
        return currentModelObj.get(currentName);
        //return modelObj.get(name);
    }

    @Override
    public <T> T getAs(String name, Class<T> type)
            throws Exception {
        return (is(name, type)) ? (T) modelObj.get(name) : null;
    }

    @Override
    public boolean has(String name) {
        return modelObj.containsKey(name);
    }

    @Override
    public <T> boolean is(String name, Class<T> type) {
        return (has(name) && type != null && type.isAssignableFrom(get(name).getClass()));
    }

    public JSONObject getJSON() {
        return modelObj;
    }

    @Override
    public String toString() {
        return getJSON().toJSONString();
    }
}
