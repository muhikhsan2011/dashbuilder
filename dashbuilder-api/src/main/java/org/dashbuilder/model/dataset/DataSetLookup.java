/**
 * Copyright (C) 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.model.dataset;

import java.util.List;
import java.util.ArrayList;

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * A data set look up request.
 */
@Portable
public class DataSetLookup implements DataSetRef {

    /**
     * The UUID of the data set to retrieve.
     */
    protected String dataSetUUID = null;

    /**
     * The starting row offset of the target data set.
     */
    protected int rowOffset = 0;

    /**
     * The number of rows to get.
     */
    protected int numberOfRows = 100;

    /**
     * The list of operations to apply on the target data set as part of the lookup operation.
     */
    protected List<DataSetOp> operationList = new ArrayList<DataSetOp>();

    public DataSetLookup() {
    }

    public void setDataSetUUID(String dataSetUUID) {
        this.dataSetUUID = dataSetUUID;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public DataSetLookup(String dataSetUUID, DataSetOp... ops) {
        this.dataSetUUID = dataSetUUID;
        for (DataSetOp op : ops) {
            operationList.add(op);
        }
    }

    public String getDataSetUUID() {
        return dataSetUUID;
    }

    public List<DataSetOp> getOperationList() {
        return operationList;
    }

    public DataSetLookup addOperation(DataSetOp op) {
        operationList.add(op);
        return this;
    }

    public boolean equals(Object obj) {
        try {
            DataSetLookup other = (DataSetLookup) obj;
            if (dataSetUUID == null || other.dataSetUUID == null) return false;
            if (!dataSetUUID.equals(other.dataSetUUID)) return false;
            if (rowOffset != other.rowOffset) return false;
            if (numberOfRows != other.numberOfRows) return false;
            if (operationList.size() != other.operationList.size()) return false;
            for (int i = 0; i < operationList.size(); i++) {
                DataSetOp op = operationList.get(i);
                DataSetOp otherOp = other.operationList.get(i);
                if (!op.equals(otherOp)) return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}