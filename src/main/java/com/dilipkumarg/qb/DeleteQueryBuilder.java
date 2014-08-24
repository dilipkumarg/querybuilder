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

package com.dilipkumarg.qb;

import com.dilipkumarg.qb.core.AbstractQueryBuilder;
import com.dilipkumarg.qb.core.NonAliasBasedWhereClause;
import com.dilipkumarg.qb.core.WhereClauseBuilder;
import com.dilipkumarg.qb.models.SqlQuery;
import com.dilipkumarg.qb.models.SqlTable;
import com.dilipkumarg.qb.models.TableColumn;
import com.dilipkumarg.qb.models.WhereCondition;

/**
 * @author Dilip Kumar.
 * @since 1/7/14
 */
public class DeleteQueryBuilder extends AbstractQueryBuilder implements WhereClauseBuilder {
    private static final String DELETE_TEMPLATE = "DELETE FROM %s %s";

    private final WhereClauseBuilder whereDelegator;

    public DeleteQueryBuilder(SqlTable table) {
        super(table);
        whereDelegator = new NonAliasBasedWhereClause();
    }

    @Override
    public DeleteQueryBuilder where(TableColumn column, Object value) {
        whereDelegator.where(column, value);
        return this;
    }

    @Override
    public DeleteQueryBuilder where(WhereCondition... conditions) {
        whereDelegator.where(conditions);
        return this;
    }

    @Override
    public SqlQuery buildWhere() {
        return whereDelegator.buildWhere();
    }

    @Override
    public SqlQuery build() {
        SqlQuery whereQuery = buildWhere();
        return new SqlQuery(String.format(DELETE_TEMPLATE, getTable().getTableName(), whereQuery.getQuery()),
                whereQuery.getArgs());
    }
}
