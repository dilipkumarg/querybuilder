/*
 * Copyright 2014-15 Dilip Kumar
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dilipkumarg.qb.core;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Dilip Kumar.
 * @since 3/7/14
 */
public class OnClauseBuilder extends AliasBasedWhereClause {
    private static final String ON_TEMPLATE = "ON (%s)";

    @Override
    protected String buildWhereString(List<String> strings) {
        String conditionsString = StringUtils.join(strings, WHERE_SEPARATOR);
        return String.format(ON_TEMPLATE, conditionsString);
    }
}
