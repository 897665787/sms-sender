package com.jqdi.smssender.springbootdemo.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jqdi.smssender.springbootdemo.sms.AsyncSmsSender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmsJob {

	@Autowired
	private AsyncSmsSender asyncSmsSender;

	// 分布式环境下建议修改为分布式调度，比如xxljob
	@Scheduled(cron = "0/5 * * * * ? ")
	public void exePreTimeSend() {
		log.info("exePreTimeSend start");
		List<Integer> idList = null;

		int limit = 1000;
		do {
			idList = asyncSmsSender.select4PreTimeSend(limit);

			log.info("size:{}", idList.size());
			for (Integer id : idList) {
				asyncSmsSender.exePreTimeSend(id);
			}
		} while (idList.size() == limit);
		log.info("exePreTimeSend end");
	}
}
