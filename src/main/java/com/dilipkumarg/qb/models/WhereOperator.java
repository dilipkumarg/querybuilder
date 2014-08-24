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
public enum WhereOperator {
    EQUALS("="),
    NOT_EQUALS("<>"),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_EQUALS("<="),
    GREATER_THAN_EQUALS(">="),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE");

    private final String operator;

    WhereOperator(String operator) {
        this.operator = operator;
    }

    public String join(String operand1, String operand2) {
        return operand1 + " " + operator + " " + operand2;
    }

    public String joinWithPlaceHolder(String operand) {
        return join(operand, com.dilipkumarg.qb.core.QueryBuilder.PLACE_HOLDER);
    }

}
