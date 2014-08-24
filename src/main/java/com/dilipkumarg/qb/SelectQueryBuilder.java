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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.dilipkumarg.qb.core.AbstractQueryBuilder;
import com.dilipkumarg.qb.core.AliasBasedWhereClause;
import com.dilipkumarg.qb.core.JoinClauseBuilder;
import com.dilipkumarg.qb.core.JoinType;
import com.dilipkumarg.qb.core.NonAliasBasedWhereClause;
import com.dilipkumarg.qb.core.OrderByEntry;
import com.dilipkumarg.qb.core.WhereClauseBuilder;
import com.dilipkumarg.qb.models.SqlQuery;
import com.dilipkumarg.qb.models.SqlTable;
import com.dilipkumarg.qb.models.TableColumn;
import com.dilipkumarg.qb.models.WhereCondition;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public class SelectQueryBuilder extends AbstractQueryBuilder implements WhereClauseBuilder {
    private static final String SELECT_TEMPLATE = "%s %s FROM %s";
    private static final String ORDER_BY_TEMPLATE = "ORDER BY %s";
    private static final String SELECT_ALL = "*";
    private static final String SELECT = "SELECT";
    private static final String SELECT_DISTINCT = "SELECT DISTINCT";

    private final boolean WITH_ALIAS = true;
    private final List<TableColumn> selectedColumns;
    private final Set<OrderByEntry> orderByEntries;
    private final WhereClauseBuilder whereDelegator;
    private final JoinClauseBuilder joinDelegator;
    private boolean distinct = false;

    public SelectQueryBuilder(SqlTable table) {
        super(table);
        whereDelegator = WITH_ALIAS ? new AliasBasedWhereClause() : new NonAliasBasedWhereClause();
        selectedColumns = Lists.newArrayList();
        orderByEntries = Sets.newLinkedHashSet();
        joinDelegator = new JoinClauseBuilder();
    }

    @Override
    public SelectQueryBuilder where(TableColumn column, Object value) {
        whereDelegator.where(column, value);
        return this;
    }

    @Override
    public SelectQueryBuilder where(WhereCondition... conditions) {
        whereDelegator.where(conditions);
        return this;
    }

    @Override
    public SqlQuery buildWhere() {
        return whereDelegator.buildWhere();
    }

    /**
     * Selects only given columns from the table.
     *
     * @param columns
     * @return {@link SelectQueryBuilder}.
     */
    public SelectQueryBuilder list(TableColumn... columns) {
        selectedColumns.addAll(Arrays.asList(columns));
        return this;
    }

    /**
     * @return true if {@link #distinct} added.
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Adds 'DISTINCT' to query.
     *
     * @return current {@link SelectQueryBuilder} instance.
     */
    public SelectQueryBuilder distinct() {
        this.distinct = true;
        return this;
    }

    /**
     * Adds the given {@link com.dilipkumarg.qb.core.OrderByEntry} to the builder. It won't allow duplicate {@link
     * TableColumn}.
     *
     * @param columns
     * @return {@link SelectQueryBuilder}.
     */
    public SelectQueryBuilder orderBy(OrderByEntry... columns) {
        orderByEntries.addAll(Arrays.asList(columns));
        return this;
    }

    /**
     * Generates the list of selected fields.
     *
     * @return {@value #SELECT_ALL} if no fields selected, otherwise generates {@value #SEPARATOR} separated selected
     * fields.
     */
    public String buildSelectedFieldsString() {
        if (!selectedColumns.isEmpty()) {
            List<String> cols = Lists.newArrayList();
            for (TableColumn column : selectedColumns) {
                cols.add(column.getFieldName(WITH_ALIAS));
            }
            return StringUtils.join(cols, SEPARATOR);
        } else {
            return SELECT_ALL;
        }
    }


    /**
     * Generates String with selected orderBy elements.
     *
     * @return {@link String}Generated String.
     */
    public String buildOrderByString() {
        if (!orderByEntries.isEmpty()) {
            List<String> cols = Lists.newArrayList();
            for (OrderByEntry entry : orderByEntries) {
                cols.add(entry.buildOrderByEntry(WITH_ALIAS));
            }
            return String.format(ORDER_BY_TEMPLATE, StringUtils.join(cols, SEPARATOR));
        } else {
            return "";
        }
    }

    /**
     * Adds given joinType for {@link SqlTable} with given condition.You can given multiple conditions for join. You can
     * give Two give Two {@link SqlTable} of same type with Different Alias Name. If you give two {@link SqlTable} with
     * same name and alias it will ignore second one.
     *
     * @param table
     * @param type
     * @param conditions
     * @return {@link com.dilipkumarg.qb.SelectQueryBuilder} instance.
     */
    public SelectQueryBuilder join(SqlTable table, JoinType type, WhereCondition... conditions) {
        joinDelegator.join(table, type, conditions);
        return this;
    }

    /**
     * Adds innerJoin for given to {@link SqlTable} with given condition.You can given multiple conditions for join. You
     * can give Two give Two {@link SqlTable} of same type with Different Alias Name. If you give two {@link SqlTable}
     * with same name and alias it will ignore second one.
     *
     * @param table
     * @param conditions
     * @return {@link com.dilipkumarg.qb.SelectQueryBuilder} instance.
     */
    public SelectQueryBuilder innerJoin(SqlTable table, WhereCondition... conditions) {
        return this.join(table, JoinType.INNER_JOIN, conditions);
    }

    /**
     * Adds {@link com.dilipkumarg.qb.core.JoinType#INNER_JOIN} for given to {@link SqlTable} with given condition.You
     * can given multiple conditions for join.You can give Two give Two {@link SqlTable} of same type with Different
     * Alias Name. If you give two {@link SqlTable} with same name and alias it will ignore second one.
     *
     * @param table
     * @param conditions
     * @return {@link com.dilipkumarg.qb.SelectQueryBuilder} instance.
     */
    public SelectQueryBuilder leftJoin(SqlTable table, WhereCondition... conditions) {
        return this.join(table, JoinType.LEFT_JOIN, conditions);
    }

    /**
     * Adds {@link JoinType#RIGHT_JOIN} for given to {@link SqlTable} with given condition.You can given multiple
     * conditions for join.You can give Two give Two {@link SqlTable} of same type with Different Alias Name. If you
     * give two {@link SqlTable} with same name and alias it will ignore second one.
     *
     * @param table
     * @param conditions
     * @return {@link com.dilipkumarg.qb.SelectQueryBuilder} instance.
     */
    public SelectQueryBuilder rightJoin(SqlTable table, WhereCondition... conditions) {
        return this.join(table, JoinType.RIGHT_JOIN, conditions);
    }

    /**
     * Adds {@link JoinType#FULL_JOIN} for given to {@link SqlTable} with given condition.You can given multiple
     * conditions for join.You can give Two give Two {@link SqlTable} of same type with Different Alias Name. If you
     * give two {@link SqlTable} with same name and alias it will ignore second one.
     *
     * @param table
     * @param conditions
     * @return {@link com.dilipkumarg.qb.SelectQueryBuilder} instance.
     */
    public SelectQueryBuilder fullJoin(SqlTable table, WhereCondition... conditions) {
        return this.join(table, JoinType.FULL_JOIN, conditions);
    }

    @Override
    public SqlQuery build() {
        SqlQuery whereQuery = buildWhere();
        SqlQuery joins = joinDelegator.build();
        StringBuilder sb = new StringBuilder();
        String select = isDistinct() ? SELECT_DISTINCT : SELECT;
        sb.append(String.format(SELECT_TEMPLATE, select, buildSelectedFieldsString(),
                getTable().getTableName(WITH_ALIAS)));

        if (!joins.getQuery().isEmpty()) {
            sb.append(" ");
            sb.append(joins.getQuery());
        }

        if (!whereQuery.getQuery().isEmpty()) {
            sb.append(" ");
            sb.append(whereQuery.getQuery());
        }
        sb.append(" ");// it is a last part, so No need to validate.
        sb.append(buildOrderByString());
        return new SqlQuery(sb.toString(), ArrayUtils.addAll(joins.getArgs(), whereQuery.getArgs()));
    }

}
