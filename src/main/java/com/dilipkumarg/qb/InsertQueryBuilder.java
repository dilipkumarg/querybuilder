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
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dilipkumarg.qb.core.InsertableQueryBuilder;
import com.dilipkumarg.qb.models.SqlQuery;
import com.dilipkumarg.qb.models.SqlTable;
import com.dilipkumarg.qb.models.TableColumn;
import com.google.common.collect.Lists;

/**
 * @author Dilip Kumar.
 * @since 1/7/14
 */
public class InsertQueryBuilder extends InsertableQueryBuilder<InsertQueryBuilder> {
    private static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";

    public InsertQueryBuilder(SqlTable table) {
        super(table);
    }


    private String buildArgsKeyList(Set<TableColumn> columns) {
        List<String> keys = Lists.newArrayList();
        for (TableColumn column : columns) {
            keys.add(column.getFieldName());
        }
        return StringUtils.join(keys, SEPARATOR);
    }

    private String buildPlaceHolders(int size) {
        return StringUtils.repeat(PLACE_HOLDER, SEPARATOR, size);
    }


    @Override
    protected SqlQuery buildInsertableQuery() {
        String insertQuery = String.format(INSERT_TEMPLATE, getTable().getTableName(),
                buildArgsKeyList(getArguments().keySet()),
                buildPlaceHolders(getArguments().keySet().size()));
        return new SqlQuery(insertQuery, getArguments().values());
    }
}
