package com.jqdi.smssender.springbootdemo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jqdi.smssender.springbootdemo.entity.SmsTypeTemplateConfig;

public interface SmsTypeTemplateConfigMapper extends BaseMapper<SmsTypeTemplateConfig> {

	@Select("select * from sms_type_template_config where type = #{type} and channel = #{channel}")
	SmsTypeTemplateConfig selectByTypeChannel(@Param("type") String type, @Param("channel") String channel);
}