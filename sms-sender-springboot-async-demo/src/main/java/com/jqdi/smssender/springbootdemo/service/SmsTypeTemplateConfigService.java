package com.jqdi.smssender.springbootdemo.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jqdi.smssender.springbootdemo.entity.SmsTypeTemplateConfig;
import com.jqdi.smssender.springbootdemo.mapper.SmsTypeTemplateConfigMapper;

@Service
public class SmsTypeTemplateConfigService extends ServiceImpl<SmsTypeTemplateConfigMapper, SmsTypeTemplateConfig> {

	public SmsTypeTemplateConfig selectByTypeChannel(String type, String channel) {
		return baseMapper.selectByTypeChannel(type, channel);
	}
}
