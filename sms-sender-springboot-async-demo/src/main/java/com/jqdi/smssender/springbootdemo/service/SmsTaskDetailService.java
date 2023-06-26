package com.jqdi.smssender.springbootdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jqdi.smssender.springbootdemo.entity.SmsTaskDetail;
import com.jqdi.smssender.springbootdemo.enums.SmsTaskDetailEnum;
import com.jqdi.smssender.springbootdemo.mapper.SmsTaskDetailMapper;

@Service
public class SmsTaskDetailService extends ServiceImpl<SmsTaskDetailMapper, SmsTaskDetail> {

	public Integer updateStatusByStatus(SmsTaskDetailEnum.Status toStatus, String remark, Integer id,
			SmsTaskDetailEnum.Status fromStatus) {
		return baseMapper.updateStatusByStatus(toStatus, remark, id, fromStatus);
	}

	public Integer updateContentById(String content, Integer id) {
		return baseMapper.updateContentById(content, id);
	}

	public Integer updateSendSuccessStatus(SmsTaskDetailEnum.Status toStatus, String remark, Integer id) {
		return baseMapper.updateSendSuccessStatus(toStatus, remark, id);
	}

	public Integer updateStatusRemark(SmsTaskDetailEnum.Status toStatus, String remark, Integer id) {
		return baseMapper.updateStatusRemark(toStatus, remark, id);
	}

	public Integer updateRemark(String remark, Integer id) {
		return baseMapper.updateRemark(remark, id);
	}

	public List<Integer> select4PreTimeSend(SmsTaskDetailEnum.Status status, int limit) {
		return baseMapper.select4PreTimeSend(status, limit);
	}
}
