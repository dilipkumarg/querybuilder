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

import com.dilipkumarg.qb.models.SqlQuery;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public interface QueryBuilder {
    public static final String PLACE_HOLDER = "?";
    public static final String SEPARATOR = ",";

    /**
     * Generates the {@link SqlQuery} with given arguments.
     *
     * @return Generated {@link SqlQuery}
     */
    public SqlQuery build();
}
