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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dilipkumarg.qb.models.SqlQuery;
import com.dilipkumarg.qb.models.SqlTable;
import com.dilipkumarg.qb.models.WhereCondition;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Dilip Kumar.
 * @since 3/7/14
 */
public class JoinClauseBuilder implements JoinBuilder {
    private final Set<JoinCondition> joinConditions;

    public JoinClauseBuilder() {
        this.joinConditions = Sets.newLinkedHashSet();
    }

    public JoinClauseBuilder join(JoinCondition condition) {
        joinConditions.add(condition);
        return this;
    }

    public JoinClauseBuilder join(SqlTable table, JoinType type, WhereCondition... conditions) {
        OnClauseBuilder builder = new OnClauseBuilder();
        builder.where(conditions);
        return join(new JoinCondition(table, type, builder));
    }


    @Override
    public SqlQuery build() {
        List<Object> args = Lists.newArrayList();
        List<String> queries = Lists.newArrayList();

        for (JoinCondition condition : joinConditions) {
            SqlQuery joinQuery = condition.buildJoin();
            args.addAll(Arrays.asList(joinQuery.getArgs()));
            queries.add(joinQuery.getQuery());
        }

        return new SqlQuery(StringUtils.join(queries, " "), args);
    }
}
