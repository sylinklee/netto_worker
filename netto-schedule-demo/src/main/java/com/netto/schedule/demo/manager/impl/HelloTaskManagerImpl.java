package com.netto.schedule.demo.manager.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netto.schedule.demo.dao.HelloScheduleDao;
import com.netto.schedule.demo.manager.HelloTaskManager;
import com.netto.schedule.dto.TaskResponse;
import com.netto.schedule.manager.ScheduleTaskManager;

@Component
public class HelloTaskManagerImpl implements HelloTaskManager {
	@Autowired
	private ScheduleTaskManager taskManager;
	@Autowired
	private HelloScheduleDao helloDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void helloTask(TaskResponse<String> task, String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", msg);
		map.put("task", task.getId());
		helloDao.insert(map);
		// 保证更新业务数据和任务数据在同一个事务中
		taskManager.doneTask(task);
	}

	public ScheduleTaskManager getTaskManager() {
		return taskManager;
	}
}
