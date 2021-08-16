/*
 * Copyright 2017 ~ 2025 the original author or authors. <wanglsir@gmail.com, 983708408@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wl4g.dopaas.lcdp.dds.service.handler;

import static com.wl4g.component.common.collection.CollectionUtils2.safeList;
import static com.wl4g.component.common.lang.Assert2.notNullOf;
import static com.wl4g.component.common.log.SmartLoggerFactory.getLogger;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.wl4g.component.common.log.SmartLogger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.StringValue;

/**
 * {@link AbstractImageEvaluator}
 * 
 * @author Wangl.sir &lt;wanglsir@gmail.com, 983708408@qq.com&gt;
 * @version 2021-08-15 v1.0.0
 * @since v1.0.0
 */

public abstract class AbstractImageEvaluator implements SQLImageEvaluator {
    protected final SmartLogger log = getLogger(getClass());

    protected final EvaluatorProperties config;
    protected final JdbcTemplate jdbcTemplate;

    @Getter
    @Setter(lombok.AccessLevel.PROTECTED)
    private List<String> undoDeleteSqls; // due insert SQL.
    @Getter
    @Setter(lombok.AccessLevel.PROTECTED)
    private List<String> undoInsertSqls; // due delete SQL.
    @Getter
    @Setter(lombok.AccessLevel.PROTECTED)
    private List<String> undoUpdateSqls; // due update SQL.

    public AbstractImageEvaluator(EvaluatorProperties config, JdbcTemplate jdbcTemplate) {
        this.config = notNullOf(config, "config");
        this.jdbcTemplate = notNullOf(jdbcTemplate, "jdbcTemplate");
    }

    @Override
    public List<String> getAllUndoSQLs() {
        // Each handling will only be one of them.
        return nonNull(undoDeleteSqls) ? undoDeleteSqls : (nonNull(undoInsertSqls) ? undoInsertSqls : undoUpdateSqls);
    }

    protected List<OperationRecord> findOperationRecords(String selectSQL) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(selectSQL);
        return safeList(result).stream().map(r -> new OperationRecord(r)).collect(toList());
    }

    protected String getInsertKeyword() {
        return "INSERT";
    }

    protected String getUpdateKeyword() {
        return "UPDATE";
    }

    protected boolean needQuotationMark(Object value) {
        return value instanceof String || value instanceof StringValue || value instanceof Date || value instanceof DateValue
                || value instanceof java.sql.Date;
    }

    static class OperationRecord extends LinkedHashMap<String, Object> {
        private static final long serialVersionUID = -3736621942100254300L;

        public OperationRecord() {
        }

        public OperationRecord(Map<String, Object> record) {
            putAll(record);
        }

        public String getString(String column) {
            Object value = get(column);
            return isNull(value) ? null : value.toString();
        }

        public Integer getInteger(String column) {
            Object value = get(column);
            return isNull(value) ? null : new Integer(value.toString());
        }

        public Long getLong(String column) {
            Object value = get(column);
            return isNull(value) ? null : new Long(value.toString());
        }

        public Float getFloat(String column) {
            Object value = get(column);
            return isNull(value) ? null : new Float(value.toString());
        }

        public Double getDouble(String column) {
            Object value = get(column);
            return isNull(value) ? null : new Double(value.toString());
        }

    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    @ToString
    static class EvaluatorProperties implements Serializable {
        private static final long serialVersionUID = 4320766245447481229L;

        private int limitOperationRecords = DEFAULT_LIMIT_OPERATION_RECORDS;

        public static final int DEFAULT_LIMIT_OPERATION_RECORDS = 5000;
    }

}
