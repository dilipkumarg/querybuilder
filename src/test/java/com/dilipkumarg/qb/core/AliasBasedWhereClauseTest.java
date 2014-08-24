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

import org.junit.Before;
import org.junit.Test;

import com.dilipkumarg.qb.QPerson;
import com.dilipkumarg.qb.models.WhereCondition;
import com.dilipkumarg.qb.models.WhereOperator;

import static org.junit.Assert.assertEquals;

public class AliasBasedWhereClauseTest {
    private AliasBasedWhereClause builder;
    QPerson person = new QPerson("p");

    @Before
    public void setUp() throws Exception {
        builder = new AliasBasedWhereClause();
    }

    @Test
    public void testGetWhereCondition() throws Exception {
        WhereCondition condition = new WhereCondition(person.name, WhereOperator.EQUALS, person.lastName);
        assertEquals(condition.buildConditionWithAlias(), builder.getWhereCondition(condition));
    }
}