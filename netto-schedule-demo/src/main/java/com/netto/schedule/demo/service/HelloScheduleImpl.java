package com.netto.schedule.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netto.schedule.ScheduleParam;
import com.netto.schedule.demo.manager.HelloTaskManager;
import com.netto.schedule.demo.mock.OuterMockService;
import com.netto.schedule.demo.mock.OuterMockService.ServiceResponse;
import com.netto.schedule.dto.TaskResponse;
import com.netto.schedule.support.AbstractScheduleTaskProcess;

@Component
public class HelloScheduleImpl extends AbstractScheduleTaskProcess<TaskResponse<String>> {
	@Autowired
	private HelloTaskManager helloManager;
	@Autowired
	private OuterMockService outerService;

	@Override
	protected List<TaskResponse<String>> selectTasks(ScheduleParam param, Integer curServer) {
		return this.helloManager.getTaskManager().queryExecuteTasks("hello", param, curServer);
	}

	@Override
	protected void executeTasks(ScheduleParam param, List<TaskResponse<String>> tasks) {
		this.helloManager.getTaskManager().lockTasks(tasks);
		for (TaskResponse<String> task : tasks) {
			try {
				// 调用外部的服务
				ServiceResponse<String> res = outerService.syncMessge(task.getTaskBody());
				if (res.getSuccess()) {
					// 成功更新业务数据及任务数据
					this.helloManager.helloTask(task, res.getBody());
				} else {
					// 失败更新任务数据
					Exception e = new Exception(res.getErrorMsg());
					this.helloManager.getTaskManager().errorTask(task, e);
				}
			} catch (Throwable t) {
				// 抛出异常的也更新任务表
				this.helloManager.getTaskManager().errorTask(task, t);
			}
		}
	}

}
