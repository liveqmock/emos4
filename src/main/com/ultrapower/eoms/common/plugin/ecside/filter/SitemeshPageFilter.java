/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ultrapower.eoms.common.plugin.ecside.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.ultrapower.eoms.common.plugin.ecside.core.context.ServletRequestContext;
import com.ultrapower.eoms.common.plugin.ecside.core.context.WebContext;

//import com.opensymphony.module.sitemesh.filter.PageFilter;

/**
 * @author phorn
 */
public class SitemeshPageFilter {

//public class SitemeshPageFilter extends PageFilter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        WebContext context = new ServletRequestContext(request);
        if (ExportFilterUtils.isExported(context)) {
            chain.doFilter(request, response); // Don't Decorate
        } else {
//            super.doFilter(request, response, chain); // Decorate
        }
    }
}
