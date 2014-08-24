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
import com.dilipkumarg.qb.models.TableColumn;
import com.dilipkumarg.qb.models.WhereCondition;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public interface WhereClauseBuilder<T extends WhereClauseBuilder<T>> {

    /**
     * Adds Equal condition to the builder.
     *
     * @param column
     * @param value
     * @return {@link WhereClauseBuilder}.
     */
    T where(TableColumn column, Object value);

    /**
     * Adds the given {@link WhereCondition} to the builder.
     *
     * @param conditions
     * @return {@link WhereClauseBuilder}.
     */
    T where(WhereCondition... conditions);

    /**
     * Generates the {@link SqlQuery} for given {@link com.dilipkumarg.qb.models.WhereCondition}. If there is no
     * conditions it returns empty query.s
     *
     * @return {@link SqlQuery}.
     */
    SqlQuery buildWhere();
}
