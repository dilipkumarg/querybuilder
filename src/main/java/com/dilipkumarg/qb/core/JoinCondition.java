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
import com.dilipkumarg.qb.models.SqlTable;

/**
 * @author Dilip Kumar.
 * @since 3/7/14
 */
public class JoinCondition {
    private final String JOIN_TEMPLATE = "%s %s";

    private final SqlTable table;
    private final JoinType type;
    private final OnClauseBuilder onClauseBuilder;

    public JoinCondition(SqlTable table, JoinType type, OnClauseBuilder onClause) {
        this.table = table;
        this.type = type;
        this.onClauseBuilder = onClause;
    }

    public SqlTable getTable() {
        return table;
    }

    public JoinType getType() {
        return type;
    }

    public OnClauseBuilder getOnClauseBuilder() {
        return onClauseBuilder;
    }

    public SqlQuery buildJoin() {
        SqlQuery onQuery = onClauseBuilder.buildWhere();
        String joinQuery = String.format(JOIN_TEMPLATE, type.buildJoin(table.getTableName(true)),
                onQuery.getQuery());
        return new SqlQuery(joinQuery, onQuery.getArgs());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoinCondition)) return false;

        JoinCondition that = (JoinCondition) o;

        if (!table.equals(that.table)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return table.hashCode();
    }
}
