﻿/*
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
package com.ultrapower.eoms.common.plugin.ecside.table.cell;

import com.ultrapower.eoms.common.plugin.ecside.core.TableModel;
import com.ultrapower.eoms.common.plugin.ecside.core.bean.Column;

/**
 * All table cells need to implement the Cell interface.
 * 
 * @author Jeff Johnston
 */
public interface Cell {

    /**
     * The markup that will be used for the exports.
     */
    public String getExportDisplay(TableModel model, Column column);

    /**
     * The html that will be displayed in the table.
     */
    public String getHtmlDisplay(TableModel model, Column column);
}
