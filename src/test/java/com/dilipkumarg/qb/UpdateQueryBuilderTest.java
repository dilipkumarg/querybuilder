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
public class UpdateQueryBuilderTest {
    private QPerson person;
    private UpdateQueryBuilder builder;

    public UpdateQueryBuilderTest() {
        person = new QPerson();
    }

    @Before
    public void setUp() {
        builder = new UpdateQueryBuilder(person);
    }

    @Test
    public void testBasicUpdate() throws DuplicateArgumentException {
        builder.set(person.name, "Dilip");
        builder.set(person.age, 23);
        SqlQuery query = builder.build();

        assertNotNull(query);
        assertEquals("UPDATE PERSON SET NAME=?,AGE=?", query.getQuery());
        assertEquals(2, query.getArgs().length);
        assertEquals("Dilip", query.getArgs()[0]);
        assertEquals(23, query.getArgs()[1]);
    }

    @Test
    public void testBasicUpdateWithWhere() throws DuplicateArgumentException {
        builder.set(person.name, "Dilip");
        builder.set(person.age, 23);
        builder.where(person.age.lt(20));
        SqlQuery query = builder.build();

        assertNotNull(query);
        assertEquals("UPDATE PERSON SET NAME=?,AGE=? WHERE AGE < ?", query.getQuery());
        assertEquals(3, query.getArgs().length);
        assertEquals("Dilip", query.getArgs()[0]);
        assertEquals(23, query.getArgs()[1]);
        assertEquals(20, query.getArgs()[2]);
    }

    @Test
    public void testBasicUpdateWithMultipleWhere() throws DuplicateArgumentException {
        builder.set(person.name, "Dilip");
        builder.set(person.age, 23);
        builder.where(person.age.lt(20));
        builder.where(person.lastName.eq("TEST"));
        SqlQuery query = builder.build();

        assertNotNull(query);
        assertEquals("UPDATE PERSON SET NAME=?,AGE=? WHERE AGE < ? AND LAST_NAME = ?", query.getQuery());
        assertEquals(4, query.getArgs().length);
        assertEquals("Dilip", query.getArgs()[0]);
        assertEquals(23, query.getArgs()[1]);
        assertEquals(20, query.getArgs()[2]);
        assertEquals("TEST", query.getArgs()[3]);
    }


    @Test(expected = QueryBuilderRuntimeException.class)
    public void testInvalidUpdate() {
        builder.build();
    }

    @Test
    public void testInvalidUpdate2() {
        try {
            builder.set(person.name, "Dilip");
            builder.set(person.name, "TEST");
            fail("Builder accepting two arguments with same name.");
        } catch (DuplicateArgumentException e) {
            // do nothing.. test pass
        }
    }

    @Test
    public void testAll() throws DuplicateArgumentException {
        SqlQuery query = builder.set(person.name, "Dilip")
                .set(person.age, 23)
                .where(person.age.lt(20))
                .where(person.lastName.eq("TEST")).build();

        assertNotNull(query);
        assertEquals("UPDATE PERSON SET NAME=?,AGE=? WHERE AGE < ? AND LAST_NAME = ?", query.getQuery());
        assertEquals(4, query.getArgs().length);
        assertEquals("Dilip", query.getArgs()[0]);
        assertEquals(23, query.getArgs()[1]);
        assertEquals(20, query.getArgs()[2]);
        assertEquals("TEST", query.getArgs()[3]);
    }
}
