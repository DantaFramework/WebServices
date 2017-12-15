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

import org.apache.felix.scr.annotations.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static danta.webservices.Constants.*;


/**
 * @author   joshuaoransky
 * @since    2016-07-25
 */
@Component
@Property(name = SERVICE_PATH, value = "/api", propertyPrivate = true)
public class ReSTfulContextProcessorEngineAccessServlet
        extends AbstractDantaServlet {

    @Override
    protected void onGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        onMethod(request, response);
    }

    @Override
    protected void onPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        onMethod(request, response);
    }

    @Override
    protected void onDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        onMethod(request, response);
    }

    @Override
    protected void onPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        onMethod(request, response);
    }
}
