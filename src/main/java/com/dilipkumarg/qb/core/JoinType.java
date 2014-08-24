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

/**
 * @author Dilip Kumar.
 * @since 3/7/14
 */
public enum JoinType {
    INNER_JOIN("INNER JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
    FULL_JOIN("FULL JOIN"),
    FULL_OUTER_JOIN("FULL OUTER JOIN");

    private static final String JOIN_TEMPLATE = "%s %s";
    private final String joinType;

    JoinType(String joinType) {
        this.joinType = joinType;
    }

    public String buildJoin(String tableName) {
        return String.format(JOIN_TEMPLATE, joinType, tableName);
    }
}
