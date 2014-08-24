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

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.dilipkumarg.qb.core.InsertableQueryBuilder;
import com.dilipkumarg.qb.core.NonAliasBasedWhereClause;
import com.dilipkumarg.qb.core.WhereClauseBuilder;
import com.dilipkumarg.qb.models.SqlQuery;
import com.dilipkumarg.qb.models.SqlTable;
import com.dilipkumarg.qb.models.TableColumn;
import com.dilipkumarg.qb.models.WhereCondition;
import com.google.common.collect.Lists;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public class UpdateQueryBuilder extends InsertableQueryBuilder<UpdateQueryBuilder> implements WhereClauseBuilder {
    private static final String UPDATE_TEMPLATE = "UPDATE %s SET %s %s";
    private final WhereClauseBuilder whereDelegator;

    public UpdateQueryBuilder(SqlTable table) {
        super(table);
        whereDelegator = new NonAliasBasedWhereClause();
    }


    protected String buildArgumentsQueryString() {
        List<String> columns = Lists.newArrayList();
        for (TableColumn arg : getArguments().keySet()) {
            columns.add(arg.getFieldName() + "=" + PLACE_HOLDER);
        }
        return StringUtils.join(columns, SEPARATOR);
    }

    @Override
    protected SqlQuery buildInsertableQuery() {
        SqlQuery whereQuery = buildWhere();
        String queryString = String.format(UPDATE_TEMPLATE, getTable().getTableName(), buildArgumentsQueryString(),
                whereQuery.getQuery());

        Object[] args = ArrayUtils.addAll(getArguments().values().toArray(), whereQuery.getArgs());
        return new SqlQuery(queryString, args);
    }


    @Override
    public UpdateQueryBuilder where(TableColumn column, Object value) {
        whereDelegator.where(column, value);
        return this;
    }

    @Override
    public UpdateQueryBuilder where(WhereCondition... conditions) {
        whereDelegator.where(conditions);
        return this;
    }

    @Override
    public SqlQuery buildWhere() {
        return whereDelegator.buildWhere();
    }
}
