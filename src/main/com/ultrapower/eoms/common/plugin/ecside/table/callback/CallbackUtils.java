﻿/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ultrapower.eoms.common.plugin.ecside.table.callback;

import java.util.Collection;

import com.ultrapower.eoms.common.plugin.ecside.core.RetrievalUtils;
import com.ultrapower.eoms.common.plugin.ecside.core.TableModel;
import com.ultrapower.eoms.common.plugin.ecside.core.bean.Table;

/**
 * Deal with callback specific utils.
 * 
 * @deprecated Use RetrievalUtils now.
 * 
 * @author Jeff Johnston
 */
@Deprecated
public class CallbackUtils {
    private CallbackUtils() {
    }

    /**
     * @deprecated Use RetrievalUtils.retrieveCollection now.
     */
    @Deprecated
	public static Collection getItems(TableModel model, Table table) throws Exception {
        String items = String.valueOf(table.getItems());
        return RetrievalUtils.retrieveCollection(model.getContext(), items, table.getScope());
    }
}