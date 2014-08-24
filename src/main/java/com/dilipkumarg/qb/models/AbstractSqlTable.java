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

import com.dilipkumarg.qb.DeleteQueryBuilder;
import com.dilipkumarg.qb.InsertQueryBuilder;
import com.dilipkumarg.qb.SelectQueryBuilder;
import com.dilipkumarg.qb.UpdateQueryBuilder;

/**
 * @author Dilip Kumar.
 * @since 1/7/14
 */
public abstract class AbstractSqlTable implements SqlTable {
    private static final String AS = " ";

    protected String tableName;
    protected String tableAlias;

    protected AbstractSqlTable(String tableName, String tableAlias) {
        this.tableName = tableName;
        this.tableAlias = tableAlias;
    }

    public AbstractSqlTable(String tableName) {
        this(tableName, tableName.toLowerCase());
    }


    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getTableAlias() {
        return tableAlias;
    }

    @Override
    public String getTableNameWithAlias() {
        return getTableName() + AS + getTableAlias();
    }

    @Override
    public String getTableName(boolean withAlias) {
        return withAlias ? getTableNameWithAlias() : getTableName();
    }

    @Override
    public TableColumn createTableColumn(String fieldName) {
        return new TableColumn(fieldName, this);
    }

    /**
     * Creates new {@link com.dilipkumarg.qb.SelectQueryBuilder} for this {@link com.wavemaker.gateway
     * .commons.qb.models.SqlTable}
     *
     * @return {@link com.dilipkumarg.qb.SelectQueryBuilder} object.
     */
    public SelectQueryBuilder select() {
        return new SelectQueryBuilder(this);
    }

    /**
     * Creates new {@link com.dilipkumarg.qb.InsertQueryBuilder} for this {@link com.wavemaker.gateway
     * .commons.qb.models.SqlTable}
     *
     * @return {@link com.dilipkumarg.qb.InsertQueryBuilder} object.
     */
    public InsertQueryBuilder insert() {
        return new InsertQueryBuilder(this);
    }

    /**
     * Creates new {@link com.dilipkumarg.qb.UpdateQueryBuilder} for this {@link com.wavemaker.gateway
     * .commons.qb.models.SqlTable}
     *
     * @return {@link com.dilipkumarg.qb.UpdateQueryBuilder} object.
     */
    public UpdateQueryBuilder update() {
        return new UpdateQueryBuilder(this);
    }

    /**
     * Creates new {@link com.dilipkumarg.qb.DeleteQueryBuilder} for this {@link com.wavemaker.gateway
     * .commons.qb.models.SqlTable}
     *
     * @return {@link com.dilipkumarg.qb.DeleteQueryBuilder} object.
     */
    public DeleteQueryBuilder delete() {
        return new DeleteQueryBuilder(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSqlTable)) return false;

        AbstractSqlTable that = (AbstractSqlTable) o;

        if (!tableAlias.equals(that.tableAlias)) return false;
        if (!tableName.equals(that.tableName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableName.hashCode();
        result = 31 * result + tableAlias.hashCode();
        return result;
    }
}
