package com.netto.schedule.demo.manager;

import com.netto.schedule.dto.TaskResponse;
import com.netto.schedule.manager.ScheduleTaskManager;

public interface HelloTaskManager {
	void helloTask(TaskResponse<String> task, String msg);

	ScheduleTaskManager getTaskManager();

}
