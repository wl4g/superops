/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wl4g.dopaas.uds.elasticjob.dao.statistics;

import com.wl4g.dopaas.uds.elasticjob.domain.TaskRunningStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Task running statistics repository.
 */
@Repository
public interface TaskRunningStatisticsRepository extends JpaRepository<TaskRunningStatistics, Long> {
    
    /**
     * Find task running statistics.
     *
     * @param fromTime from date to statistics
     * @return Task running statistics
     */
    @Query("SELECT t FROM TaskRunningStatistics t where t.statisticsTime >= :fromTime")
    List<TaskRunningStatistics> findTaskRunningStatistics(@Param("fromTime") Date fromTime);
}
