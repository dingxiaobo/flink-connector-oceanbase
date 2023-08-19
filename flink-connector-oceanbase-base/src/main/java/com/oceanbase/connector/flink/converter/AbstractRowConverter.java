/*
 * Copyright (c) 2023 OceanBase
 * flink-connector-oceanbase is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *         http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package com.oceanbase.connector.flink.converter;

import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.LogicalTypeRoot;

import java.io.Serializable;

public abstract class AbstractRowConverter implements Serializable {

    public RowData.FieldGetter createFieldGetter(LogicalType type, int fieldIndex) {
        return row -> createNullableExternalConverter(type).toExternal(row, fieldIndex);
    }

    public interface FieldConverter extends Serializable {
        Object toExternal(RowData rowData, int fieldIndex);
    }

    public FieldConverter createNullableExternalConverter(LogicalType type) {
        return wrapIntoNullableExternalConverter(createExternalConverter(type), type);
    }

    protected abstract FieldConverter createExternalConverter(LogicalType type);

    protected FieldConverter wrapIntoNullableExternalConverter(
            FieldConverter fieldConverter, LogicalType type) {
        return (val, fieldIndex) -> {
            if (val == null
                    || val.isNullAt(fieldIndex)
                    || LogicalTypeRoot.NULL.equals(type.getTypeRoot())) {
                return null;
            } else {
                return fieldConverter.toExternal(val, fieldIndex);
            }
        };
    }
}