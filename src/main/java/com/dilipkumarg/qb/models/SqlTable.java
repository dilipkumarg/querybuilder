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

/**
 * @author Dilip Kumar.
 * @since 1/7/14
 */
public interface SqlTable {
    /**
     * @return {@link SqlTable} name. i.e Table Name
     */
    String getTableName();

    /**
     * @return Alias to be used for the table. eg. <code>customer As c</code>, here 'c' is the Alias name.
     */
    String getTableAlias();

    /**
     * @return Table name with Alias suffix.
     */
    String getTableNameWithAlias();

    /**
     * @param withAlias
     * @return {@link String} TableName with Alias suffix if withAlias is 'true' else returns tableName.
     */
    String getTableName(boolean withAlias);

    /**
     * Creates new {@link TableColumn} for the given {@link SqlTable}
     *
     * @param fieldName of the column.
     * @return new {@link TableColumn}.
     */
    TableColumn createTableColumn(String fieldName);
}
