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

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dilipkumarg.qb.models.SqlQuery;
import com.dilipkumarg.qb.models.TableColumn;
import com.dilipkumarg.qb.models.WhereCondition;
import com.google.common.collect.Lists;

/**
 * @author Dilip Kumar.
 * @since 1/7/14
 */
public abstract class AbstractWhereClause<T extends AbstractWhereClause<T>> implements
        WhereClauseBuilder {
    protected static final String WHERE_TEMPLATE = "WHERE %s";
    protected static final String WHERE_SEPARATOR = " AND ";
    private List<WhereCondition> conditions;

    protected AbstractWhereClause() {
        conditions = Lists.newArrayList();
    }


    /**
     * @param whereConditions
     * @return
     */
    public T where(WhereCondition... whereConditions) {
        conditions.addAll(Arrays.asList(whereConditions));
        return (T) this;
    }


    /**
     * It will add equal given condition.
     *
     * @param column
     * @param value
     * @return
     */
    public T where(TableColumn column, Object value) {
        return (T) where(column.eq(value));
    }

    /**
     * List of conditions.
     *
     * @return {@link WhereCondition} list.
     */
    protected List<WhereCondition> getConditions() {
        return conditions;
    }

    /**
     * @param condition
     * @return {@link String} Condition with/with out alias name for the {@link TableColumn}.
     */
    protected abstract String getWhereCondition(WhereCondition condition);


    /**
     * Combines the given conditions and generates the string.
     *
     * @param strings
     * @return {@link String} generated where statement.
     */
    protected String buildWhereString(List<String> strings) {
        String argsString = StringUtils.join(strings, WHERE_SEPARATOR);
        return String.format(WHERE_TEMPLATE, argsString);
    }

    /**
     * Builds the where String, For given conditions. If there is no conditions it returns empty query.
     *
     * @return Generated {@link SqlQuery}.
     */
    public SqlQuery buildWhere() {
        if (!conditions.isEmpty()) {
            List<String> strings = Lists.newArrayList();
            List<Object> values = Lists.newArrayList();
            for (WhereCondition condition : conditions) {
                strings.add(getWhereCondition(condition));
                values.addAll(condition.getValues());
            }
            return new SqlQuery(buildWhereString(strings), values.toArray());
        } else {
            return new SqlQuery("");
        }
    }
}
