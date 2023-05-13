CREATE TABLE IF NOT EXISTS `sms_template` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(16) NOT NULL COMMENT '业务类型(verifycode:验证码,market:营销活动,tips:提示信息)',
  `channel` varchar(16) NOT NULL COMMENT '发送渠道(log:打印日志（测试）,ali:阿里云,tencent:腾讯云)',
  `template_code` varchar(32) NOT NULL COMMENT '模板编码',
  `template_content` varchar(255) DEFAULT NULL COMMENT '模板内容',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_channel_templatecode` (channel, template_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板';