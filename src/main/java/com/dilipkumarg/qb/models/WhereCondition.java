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

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Dilip Kumar.
 * @since 1/7/14
 */
public class WhereCondition {
    private final TableColumn column;
    private final WhereOperator operator;
    private final Object value;
    // This list used for Sub-Where conditions.
    // eg. Where ((p.person = a.person) OR (p.name = a.name)) AND p.age=a.age
    private final List<Object> values;

    public WhereCondition(TableColumn column, WhereOperator operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.values = Lists.newArrayList();
    }

    public TableColumn getColumn() {
        return column;
    }

    public WhereOperator getOperator() {
        return operator;
    }

    /**
     * Returns the list of arguments. It won't include {@link TableColumn} objects.
     *
     * @return {@link List} of arguments.
     */
    public List<Object> getValues() {
        return values;
    }

    /**
     * Generates the condition string based on given operands.
     *
     * @param withAlias
     * @return {@link String} generated string.
     */
    protected String buildCondition(boolean withAlias) {
        // clearing previous value set
        values.clear();
        if (value instanceof TableColumn) {
            return operator.join(column.getFieldName(withAlias), ((TableColumn) value).getFieldName(withAlias));
        } else {
            // Adding value to parameters list.
            values.add(value);
            return operator.joinWithPlaceHolder(column.getFieldName(withAlias));
        }
    }

    /**
     * Generates the condition without table prefix.
     *
     * @return {@link String} generated condition.
     */
    public String buildConditionWithoutAlias() {
        return buildCondition(false);
    }

    /**
     * Generates the condition with table alias prefix.
     *
     * @return {@link String} generated condition.
     */
    public String buildConditionWithAlias() {
        return buildCondition(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WhereCondition)) return false;

        WhereCondition condition = (WhereCondition) o;

        if (!column.equals(condition.column)) return false;
        if (operator != condition.operator) return false;
        if (!value.equals(condition.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = column.hashCode();
        result = 31 * result + operator.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
