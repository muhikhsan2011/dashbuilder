/**
 * Copyright (C) 2014 JBoss Inc
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
package org.dashbuilder.storage.memory.group;

import java.util.Collections;
import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang.StringUtils;
import org.dashbuilder.model.dataset.DataColumn;
import org.dashbuilder.model.dataset.group.DateIntervalType;
import org.dashbuilder.model.dataset.group.GroupColumn;

import static org.dashbuilder.model.dataset.group.DateIntervalType.*;

/**
 * Interval builder for date columns which generates a fixed number of intervals for a given interval size.
 * <p>The only intervals sizes supported are: QUARTER, MONTH, DAY_OF_WEEK, HOUR, MINUTE & SECOND.</p>
 */
@ApplicationScoped
public class IntervalBuilderFixedDate implements IntervalBuilder {

    /** List of the only DateIntervalType's supported as fixed date intervals. */
    private DateIntervalType[] FIXED_INTERVALS_SUPPORTED = new DateIntervalType[] {
            QUARTER, MONTH, DAY_OF_WEEK, HOUR, MINUTE, SECOND};


    public IntervalList build(DataColumn column, GroupColumn groupColumn) {
        IntervalList intervalList = createIntervalList(groupColumn);

        // Reverse intervals if requested
        boolean asc = groupColumn.isAscendingOrder();
        if (!asc) Collections.reverse(intervalList);

        // Index the values
        intervalList.indexValues(column.getValues());
        return intervalList;
    }

    public IntervalList createIntervalList(GroupColumn groupColumn) {
        DateIntervalType type = DateIntervalType.getByName(groupColumn.getIntervalSize());
        if (QUARTER.equals(type)) {
            return new IntervalListQuarter(groupColumn);
        }
        if (MONTH.equals(type)) {
            return new IntervalListMonth(groupColumn);
        }
        if (DAY_OF_WEEK.equals(type)) {
            return new IntervalListDayOfWeek(groupColumn);
        }
        if (HOUR.equals(type)) {
            return new IntervalListHour(groupColumn);
        }
        if (MINUTE.equals(type)) {
            return new IntervalListMinute(groupColumn);
        }
        if (SECOND.equals(type)) {
            return new IntervalListSecond(groupColumn);
        }
        throw new IllegalArgumentException("Interval size '" + groupColumn.getIntervalSize() + "' not supported for " +
                "fixed date intervals. The only supported sizes are: " + StringUtils.join(FIXED_INTERVALS_SUPPORTED, ","));
    }
}