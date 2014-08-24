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

import com.dilipkumarg.qb.core.JoinType;
import com.dilipkumarg.qb.models.SqlQuery;

import static org.junit.Assert.assertEquals;

/**
 * @author Dilip Kumar.
 * @since 2/7/14
 */
public class SelectQueryBuilderTest {

    private QPerson person;
    private SelectQueryBuilder builder;

    public SelectQueryBuilderTest() {
        person = new QPerson();
    }

    @Before
    public void setUp() {
        builder = new SelectQueryBuilder(person);
    }

    @Test
    public void testBasicSelect() {
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testSelectWithWhere() {
        builder.where(person.name.eq("TEST"));
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person WHERE person.NAME = ?", sqlQuery.getQuery());
        assertEquals(1, sqlQuery.getArgs().length);
        assertEquals("TEST", sqlQuery.getArgs()[0]);
    }

    @Test
    public void testSelectMultipleWhere() {
        builder.where(person.name.eq("TEST1"));
        builder.where(person.lastName.like("TEST2"));
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person WHERE person.NAME = ? AND person.LAST_NAME LIKE ?",
                sqlQuery.getQuery());
        assertEquals(2, sqlQuery.getArgs().length);
        assertEquals("TEST1", sqlQuery.getArgs()[0]);
        assertEquals("TEST2", sqlQuery.getArgs()[1]);
    }

    @Test
    public void testSelectMultipleWheres() {
        //builder.where(person.name.eq("TEST").and(person.lastName.eq("TEST2")))
        builder.where(person.name.eq("TEST1"), person.lastName.like("TEST2"));
        builder.where(person.age.lt(20));
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person WHERE person.NAME = ? AND person.LAST_NAME LIKE ? AND person.AGE" +
                        " < ?",
                sqlQuery.getQuery());
        assertEquals(3, sqlQuery.getArgs().length);
        assertEquals("TEST1", sqlQuery.getArgs()[0]);
        assertEquals("TEST2", sqlQuery.getArgs()[1]);
        assertEquals(20, sqlQuery.getArgs()[2]);
    }

    @Test
    public void testList() {
        builder.list(person.name);
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT person.NAME FROM PERSON person", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testEmptyList() {
        builder.list();
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testMultipleList() {
        builder.list(person.name);
        builder.list(person.age);
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT person.NAME,person.AGE FROM PERSON person", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testListWithWhere() {
        builder.list(person.name);
        builder.list(person.age);
        builder.where(person.name.eq("TEST"));
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT person.NAME,person.AGE FROM PERSON person WHERE person.NAME = ?", sqlQuery.getQuery());
        assertEquals(1, sqlQuery.getArgs().length);
        assertEquals("TEST", sqlQuery.getArgs()[0]);
    }

    @Test
    public void testOrderBy() {
        builder.orderBy(person.age.asc());
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person ORDER BY person.AGE ASC", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testMultipleOrderBy() {
        builder.orderBy(person.age.asc());
        builder.orderBy(person.name.desc());
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person ORDER BY person.AGE ASC,person.NAME DESC", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testSimpleOrderBy() {
        builder.orderBy();
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testMultipleOrderByInOneShot() {
        builder.orderBy(person.age.asc(), person.name.desc());
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person ORDER BY person.AGE ASC,person.NAME DESC", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testInvalidOrderBy() {
        builder.orderBy(person.age.asc(), person.age.desc());
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person ORDER BY person.AGE ASC", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }


    @Test
    public void testInvalidOrderByWithMoreArgs() {
        builder.orderBy(person.age.asc(), person.name.desc(), person.age.desc());
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person ORDER BY person.AGE ASC,person.NAME DESC", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testOrderByWitList() {
        builder.list(person.name);
        builder.orderBy(person.age.asc());
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT person.NAME FROM PERSON person ORDER BY person.AGE ASC", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testDistinct() {
        builder.distinct();
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT DISTINCT * FROM PERSON person", sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testJoin() {
        QPerson person1 = new QPerson("p");
        builder.join(person1, JoinType.INNER_JOIN, person.name.eq(person1.name));
        SqlQuery sqlQuery = builder.build();
        assertEquals("SELECT * FROM PERSON person INNER JOIN PERSON p ON (person.NAME = p.NAME)",
                sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testMultipleJoins() {
        QPerson person1 = new QPerson("p");
        QPerson person2 = new QPerson("q");
        builder.leftJoin(person1, person.name.eq(person1.name));
        builder.rightJoin(person2, person1.lastName.like(person2.lastName));
        SqlQuery sqlQuery = builder.build();
        assertEquals(
                "SELECT * FROM PERSON person LEFT JOIN PERSON p ON (person.NAME = p.NAME) RIGHT JOIN PERSON q " + "ON (p.LAST_NAME LIKE q.LAST_NAME)",
                sqlQuery.getQuery());
        assertEquals(0, sqlQuery.getArgs().length);
    }

    @Test
    public void testAll() {
        SqlQuery sqlQuery = builder.list(person.name)
                .list(person.age, person.lastName)
                .where(person.name.eq("TEST"))
                .orderBy(person.name.desc())
                .distinct()
                .build();
        assertEquals("SELECT DISTINCT person.NAME,person.AGE,person.LAST_NAME FROM PERSON person WHERE person.NAME" +
                " = ? " +
                "ORDER BY person.NAME DESC", sqlQuery.getQuery());
        assertEquals(1, sqlQuery.getArgs().length);
        assertEquals("TEST", sqlQuery.getArgs()[0]);
    }


}
