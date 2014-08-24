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

import org.junit.Before;
import org.junit.Test;

import com.dilipkumarg.qb.models.SqlQuery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public class DeleteQueryBuilderTest {
    private QPerson person;
    private DeleteQueryBuilder builder;

    public DeleteQueryBuilderTest() {
        person = new QPerson();
    }

    @Before
    public void setUp() {
        builder = new DeleteQueryBuilder(person);
    }

    @Test
    public void testBasicDelete() {
        SqlQuery query = builder.build();
        assertNotNull(query);
        assertEquals("DELETE FROM PERSON", query.getQuery());
        assertEquals(0, query.getArgs().length);
    }

    @Test
    public void testDeleteWithWhere() {
        builder.where(person.name.eq("TEST"));
        SqlQuery query = builder.build();
        assertNotNull(query);
        assertEquals("DELETE FROM PERSON WHERE NAME = ?", query.getQuery());
        assertEquals(1, query.getArgs().length);
        assertEquals("TEST", query.getArgs()[0]);
    }

    @Test
    public void testDeleteWithColumnWhere() {
        builder.where(person.name.eq(person.lastName));
        SqlQuery query = builder.build();
        assertEquals("DELETE FROM PERSON WHERE NAME = LAST_NAME", query.getQuery());
        assertEquals(0, query.getArgs().length);
    }

    @Test
    public void testDeleteWithMultipleWhere() {
        builder.where(person.name.eq("TEST"));
        builder.where(person.age.le(20));
        SqlQuery query = builder.build();
        assertNotNull(query);
        assertEquals("DELETE FROM PERSON WHERE NAME = ? AND AGE <= ?", query.getQuery());
        assertEquals(2, query.getArgs().length);
        assertEquals("TEST", query.getArgs()[0]);
        assertEquals(20, query.getArgs()[1]);
    }

    @Test
    public void testAll() {
        SqlQuery query = builder.where(person.name.eq("TEST"))
                .where(person.age.le(20))
                .build();
        assertNotNull(query);
        assertEquals("DELETE FROM PERSON WHERE NAME = ? AND AGE <= ?", query.getQuery());
        assertEquals(2, query.getArgs().length);
        assertEquals("TEST", query.getArgs()[0]);
        assertEquals(20, query.getArgs()[1]);
    }
}
