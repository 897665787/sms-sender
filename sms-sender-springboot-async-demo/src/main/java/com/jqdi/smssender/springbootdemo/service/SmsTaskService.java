package com.jqdi.smssender.springbootdemo.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jqdi.smssender.springbootdemo.entity.SmsTask;
import com.jqdi.smssender.springbootdemo.mapper.SmsTaskMapper;

@Service
public class SmsTaskService extends ServiceImpl<SmsTaskMapper, SmsTask> {
}
