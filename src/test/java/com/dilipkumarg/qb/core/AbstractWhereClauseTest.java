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

public class AbstractWhereClauseTest {
    private AbstractWhereClause whereClause;
    private final QPerson person = new QPerson("p");

    @Before
    public void setUp() throws Exception {
        whereClause = new NonAliasBasedWhereClause();
    }

    @Test
    public void testWhere() throws Exception {
        whereClause.where(new WhereCondition(person.name, WhereOperator.EQUALS, "TEST"));
        assertEquals(1, whereClause.getConditions().size());

    }

    @Test
    public void testMultipleWhere() {
        whereClause.where(new WhereCondition(person.age, WhereOperator.GREATER_THAN, 10));
        assertEquals(1, whereClause.getConditions().size());
        whereClause.where(person.age.eq(20), person.name.like("TEST%"));
        assertEquals(3, whereClause.getConditions().size());
    }

    @Test
    public void testMultipleWhereDetailed() {
        WhereCondition condition1 = person.age.lt(10);
        WhereCondition condition2 = person.name.eq("TEST");
        WhereCondition condition3 = person.lastName.like("TEST%");
        whereClause.where(condition1);
        whereClause.where(condition2, condition3);
        assertEquals(3, whereClause.getConditions().size());
        assertEquals(condition1, whereClause.getConditions().get(0));
        assertEquals(condition2, whereClause.getConditions().get(1));
        assertEquals(condition3, whereClause.getConditions().get(2));
    }

    @Test
    public void testWhere1() throws Exception {
        whereClause.where(person.name, "TEST");
        assertEquals(1, whereClause.getConditions().size());
        assertEquals(new WhereCondition(person.name, WhereOperator.EQUALS, "TEST"), whereClause.getConditions().get(0));
    }

    @Test
    public void testBuildWhere() throws Exception {

    }
}