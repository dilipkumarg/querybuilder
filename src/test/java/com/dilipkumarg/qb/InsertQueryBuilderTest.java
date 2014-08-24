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

import com.dilipkumarg.qb.exceptions.DuplicateArgumentException;
import com.dilipkumarg.qb.exceptions.QueryBuilderRuntimeException;
import com.dilipkumarg.qb.models.SqlQuery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public class InsertQueryBuilderTest {
    private QPerson person;
    private InsertQueryBuilder builder;

    public InsertQueryBuilderTest() {
        person = new QPerson();
    }


    @Before
    public void setUp() {
        builder = new InsertQueryBuilder(person);
    }

    @Test
    public void testBasicInsert() throws DuplicateArgumentException {
        builder.set(person.name, "Dilip");
        builder.set(person.age, 20);
        SqlQuery query = builder.build();
        assertNotNull(query);
        assertNotNull(query.getQuery());
        assertEquals("INSERT INTO PERSON (NAME,AGE) VALUES (?,?)", query.getQuery());
        assertEquals(2, query.getArgs().length);
        assertEquals("Dilip", query.getArgs()[0]);
        assertEquals(20, query.getArgs()[1]);
    }

    @Test(expected = QueryBuilderRuntimeException.class)
    public void testInvalidInsert() {
        builder.build();
    }

    @Test
    public void testMultipleInvalidInsert() {
        try {
            builder.set(person.name, "TEST");
            builder.set(person.name, "TEST2");
            fail("Builder Accepting two arguments with same name");
        } catch (DuplicateArgumentException e) {
            // do nothing.. test pass
        }
    }

    @Test
    public void testAll() throws DuplicateArgumentException {
        SqlQuery query = builder.set(person.name, "Dilip")
                .set(person.age, 20)
                .build();
        assertNotNull(query);
        assertNotNull(query.getQuery());
        assertEquals("INSERT INTO PERSON (NAME,AGE) VALUES (?,?)", query.getQuery());
        assertEquals(2, query.getArgs().length);
        assertEquals("Dilip", query.getArgs()[0]);
        assertEquals(20, query.getArgs()[1]);
    }
}
