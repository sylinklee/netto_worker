package com.netto.schedule.demo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netto.schedule.dto.TaskRequest;
import com.netto.schedule.dto.TaskResponse;
import com.netto.schedule.manager.ScheduleTaskManager;

@Component
public class InitScheduleData implements InitializingBean {
	private static final Logger logger = Logger.getLogger(InitScheduleData.class);
	@Autowired
	private ScheduleTaskManager taskManager;

	public InitScheduleData() {
		logger.info("初始化对象InitScheduleData!");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("调用初始化方法....");
		for (int i = 0; i < 1000; i++) {
			TaskRequest<String> req = new TaskRequest<String>();
			req.setTaskType("hello");
			req.setTaskObject("hello schedule " + i);
			req.setFingerPrint(req.getTaskType() + "," + req.getTaskObject());
			TaskResponse<String> res = this.taskManager.queryTaskByFingerprint(req.getTaskType(), req.getFingerPrint());
			if (res == null) {
				this.taskManager.submitTask(req);
			} else {
				this.taskManager.resetTask(req.getTaskType(), req.getFingerPrint(), 0, 0);
			}

		}

	}

}
