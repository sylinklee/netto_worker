package com.netto.schedule.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netto.schedule.ScheduleParam;
import com.netto.schedule.demo.manager.HelloTaskManager;
import com.netto.schedule.dto.TaskResponse;
import com.netto.schedule.support.AbstractScheduleTaskProcess;

@Component
public class HelloScheduleImpl extends AbstractScheduleTaskProcess<TaskResponse<String>> {
	@Autowired
	private HelloTaskManager helloManager;

	@Override
	protected List<TaskResponse<String>> selectTasks(ScheduleParam param, Integer curServer) {
		return helloManager.queryExecuteTasks("hello", param, curServer);
	}

	@Override
	protected void executeTasks(ScheduleParam param, List<TaskResponse<String>> tasks) {
		this.helloManager.lockTasks(tasks);
		for (TaskResponse<String> task : tasks) {
			helloManager.helloTask(task);
		}
	}

}
