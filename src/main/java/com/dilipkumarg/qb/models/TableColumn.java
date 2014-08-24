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
package com.dilipkumarg.qb.models;

import com.dilipkumarg.qb.core.OrderByEntry;
import com.dilipkumarg.qb.core.OrderType;

/**
 * @author Dilip Kumar.
 * @since 1/7/14
 */
public class TableColumn {
    private final String fieldName;
    private final SqlTable table;

    public TableColumn(String fieldName, SqlTable table) {
        this.fieldName = fieldName;
        this.table = table;
    }

    /**
     * @return Column name in the table.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @return Alias name of the table.
     */
    public String getTableAlias() {
        return table.getTableAlias();
    }

    /**
     * @return Column name with the Table alias.
     */
    public String getFieldNameWithAlias() {
        return getTableAlias() + "." + getFieldName();
    }

    /**
     * @param withAlias
     * @return {@link String} field name with alias if withAlias 'true' else returns fieldName.
     */
    public String getFieldName(boolean withAlias) {
        return withAlias ? getFieldNameWithAlias() : getFieldName();
    }

    /**
     * Generates new {@link OrderByEntry} with given {@link com.dilipkumarg.qb.core.OrderType}.
     *
     * @param type
     * @return Generated {@link OrderByEntry}.
     */
    public OrderByEntry orderBy(OrderType type) {
        return new OrderByEntry(this, type);
    }

    /**
     * Generates new {@link OrderByEntry} with ASC {@link com.dilipkumarg.qb.core.OrderType}.
     *
     * @return Generated {@link OrderByEntry}.
     */
    public OrderByEntry asc() {
        return orderBy(OrderType.ASC);
    }

    /**
     * Generates new {@link OrderByEntry} with DESC {@link com.dilipkumarg.qb.core.OrderType}.
     *
     * @return Generated {@link OrderByEntry}.
     */
    public OrderByEntry desc() {
        return orderBy(OrderType.DESC);
    }

    /**
     * Creates new {@link WhereCondition} for given operator.
     *
     * @param operator
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition custom(WhereOperator operator, Object value) {
        return new WhereCondition(this, operator, value);
    }

    /**
     * Creates new {@link WhereCondition} for equals '=' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition eq(Object value) {
        return custom(WhereOperator.EQUALS, value);
    }

    /**
     * Creates new {@link WhereCondition} for not equals '&lt&gt' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition ne(Object value) {
        return custom(WhereOperator.NOT_EQUALS, value);
    }

    /**
     * Creates new {@link WhereCondition} for less than '&lt' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition lt(Object value) {
        return custom(WhereOperator.LESS_THAN, value);
    }

    /**
     * Creates new {@link WhereCondition} for less than equal '&lt=' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition le(Object value) {
        return custom(WhereOperator.LESS_THAN_EQUALS, value);
    }

    /**
     * Creates new {@link WhereCondition} for greater than '&gt' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition gt(Object value) {
        return custom(WhereOperator.GREATER_THAN, value);
    }

    /**
     * Creates new {@link WhereCondition} for greater than equals '&gt=' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition ge(Object value) {
        return custom(WhereOperator.GREATER_THAN_EQUALS, value);
    }

    /**
     * Creates new {@link WhereCondition} for like 'LIKE' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition like(Object value) {
        return custom(WhereOperator.LIKE, value);
    }

    /**
     * Creates new {@link WhereCondition} for not like 'NOT LIKE' condition.
     *
     * @param value
     * @return {@link WhereCondition}
     */
    public WhereCondition notLike(Object value) {
        return custom(WhereOperator.NOT_LIKE, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableColumn)) return false;

        TableColumn column = (TableColumn) o;

        if (!fieldName.equals(column.fieldName)) return false;
        if (!table.equals(column.table)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fieldName.hashCode();
        result = 31 * result + table.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TableColumn{" +
                "fieldName='" + fieldName + '\'' +
                '}';
    }
}
