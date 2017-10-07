package com.netto.schedule.demo.manager;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netto.schedule.dto.TaskResponse;
import com.netto.schedule.manager.impl.ScheduleTaskManagerImpl;

public class HelloTaskManager extends ScheduleTaskManagerImpl {

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void helloTask(TaskResponse<String> task) {
		try {
			// TODO：业务逻辑
			// 保证更新业务数据和任务数据在同一个事务中
			this.doneTask(task);
		} catch (Exception e) {
			this.errorTask(task, e);
		}
	}
}
