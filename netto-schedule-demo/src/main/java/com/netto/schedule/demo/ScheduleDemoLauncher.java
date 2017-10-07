package com.netto.schedule.demo;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduleDemoLauncher {
	private static Logger logger = Logger.getLogger(ScheduleDemoLauncher.class);

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			new ClassPathXmlApplicationContext("spring/spring-main.xml");
			logger.info("BoneLauncher started successfully!");
			CountDownLatch latch = new CountDownLatch(1);
			latch.await();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

	}
}
