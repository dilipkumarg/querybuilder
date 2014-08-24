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

import com.dilipkumarg.qb.models.TableColumn;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public class OrderByEntry {
    private final TableColumn column;
    private final OrderType orderType;

    public OrderByEntry(TableColumn column, OrderType orderType) {
        this.column = column;
        this.orderType = orderType;
    }

    public TableColumn getColumn() {
        return column;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * Generates the {@link OrderByEntry}.
     *
     * @param withAlias
     * @return {@link String} representation of {@link OrderByEntry}.
     */
    public String buildOrderByEntry(boolean withAlias) {
        return column.getFieldName(withAlias) + " " + orderType.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderByEntry)) return false;

        OrderByEntry that = (OrderByEntry) o;

        if (column != null ? !column.equals(that.column) : that.column != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        // One column can have only one entry.
        return column.hashCode();
    }
}
