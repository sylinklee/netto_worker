package com.netto.schedule.demo.client;

import org.apache.log4j.Logger;

import com.netto.client.bean.ReferenceBean;
import com.netto.client.router.ServiceRouterFactory;
import com.netto.core.context.ServerAddressGroup;
import com.netto.schedule.IScheduleTaskProcess;
import com.netto.schedule.ScheduleParam;

public class HelloScheduleClient {
	private static Logger logger = Logger.getLogger(HelloScheduleClient.class);

	public static void main(String[] args) throws Exception {
		ServiceRouterFactory routerFactory = new ServiceRouterFactory();
		ServerAddressGroup serverGroup = new ServerAddressGroup();
		serverGroup.setServerApp("netto-schedule-demo");
		serverGroup.setServerGroup("base");
		serverGroup.setServers("127.0.0.1:23456");
		routerFactory.setServerGroup(serverGroup);

		routerFactory.afterPropertiesSet();

		ReferenceBean refer = new ReferenceBean();
		refer.setServiceName("helloSchedule");
		refer.setRouter(routerFactory.getObject());
		refer.setInterfaceClazz(IScheduleTaskProcess.class);
		refer.setProtocol("tcp");
		ScheduleParam param = new ScheduleParam();
		param.setInvokerCount(1);
		param.setClientThreadCount(10);
		param.setFetchCount(100);
		param.setExecuteCount(10);
		param.setDataRetryCount(10);
		param.setRetryTimeInterval(120);
		IScheduleTaskProcess taskProcess = (IScheduleTaskProcess) refer.getObject();
		while (true) {
			int count = taskProcess.execute(param, 0);
			logger.info("执行数量为:" + count);
			Thread.sleep(2 * 1000);
		}
	}

}
