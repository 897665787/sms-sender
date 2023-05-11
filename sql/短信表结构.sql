CREATE TABLE `t_sms_template` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(16) NOT NULL COMMENT '业务类型(verifycode:验证码,market:营销活动,tips:提示信息)',
  `template_code` varchar(32) NOT NULL COMMENT '模板编码',
  `template_content` varchar(255) DEFAULT NULL COMMENT '模板内容',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板';

CREATE TABLE `t_sms_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `template_code` varchar(32) NOT NULL COMMENT '模板编码',
  `template_param_json` varchar(255) DEFAULT NULL COMMENT '模板参数json',
  `content` varchar(255) DEFAULT NULL COMMENT '短信内容',
  `plan_send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '计划发送时间',
  `over_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '超时取消发送时间',
  `actual_send_time` datetime DEFAULT NULL COMMENT '实际发送时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(1:待发送,2:待定时发送,11:发送MQ成功,12:MQ消费成功,21:请求成功,22:请求失败,31:取消发送)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信发送任务';

CREATE TABLE `t_sms_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `channel` varchar(16) NOT NULL COMMENT '发送渠道(ali:阿里云,tencent:腾讯云)',
  `mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `sign_name` varchar(12) NOT NULL COMMENT '短信签名(2~12个字符)，如：【XX公司】，【】不用填写',
  `template_code` varchar(32) NOT NULL COMMENT '模板编码',
  `template_param_json` varchar(255) DEFAULT NULL COMMENT '模板参数json',
  `content` varchar(255) DEFAULT NULL COMMENT '短信内容',
  `result` varchar(8) NOT NULL COMMENT '结果(success:成功,fail:失败)',
  `message` varchar(255) DEFAULT NULL COMMENT '信息',
  `request_id` varchar(64) DEFAULT NULL COMMENT '请求ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信发送记录';
