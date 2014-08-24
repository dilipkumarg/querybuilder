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

import java.util.Map;

import com.dilipkumarg.qb.exceptions.DuplicateArgumentException;
import com.dilipkumarg.qb.exceptions.QueryBuilderRuntimeException;
import com.dilipkumarg.qb.models.SqlQuery;
import com.dilipkumarg.qb.models.SqlTable;
import com.dilipkumarg.qb.models.TableColumn;
import com.google.common.collect.Maps;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public abstract class InsertableQueryBuilder<T extends InsertableQueryBuilder<T>> extends AbstractQueryBuilder {
    private final Map<TableColumn, Object> arguments;

    protected InsertableQueryBuilder(SqlTable table) {
        super(table);
        // Arguments order is important so creating LinkedHashMap.
        arguments = Maps.newLinkedHashMap();
    }

    public T set(TableColumn column, Object value) throws DuplicateArgumentException {
        if (!arguments.containsKey(column)) {
            arguments.put(column, value);
        } else {
            throw new DuplicateArgumentException("Column already added, Setting column with two values not allowed:" +
                    column);
        }
        return (T) this;
    }

    /**
     * Returns Arguments Map
     *
     * @return {@link Map}.
     */
    public Map<TableColumn, Object> getArguments() {
        return arguments;
    }

    /**
     * @return Generated {@link SqlQuery}.
     */
    protected abstract SqlQuery buildInsertableQuery();

    /**
     * Builds the {@link SqlQuery} for given properties. Throws Exception when no arguments found.
     *
     * @return {@link SqlQuery}.
     * @throws com.dilipkumarg.qb.exceptions.QueryBuilderRuntimeException
     */
    @Override
    public SqlQuery build() {
        if (getArguments().size() > 0) {
            return buildInsertableQuery();
        } else {
            throw new QueryBuilderRuntimeException("You have not added any columns for insertion. Hence operation not" +
                    " permitted");
        }
    }
}
