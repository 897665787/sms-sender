一个封装了短信发送的框架，支持阿里云、腾讯云、百度云、京东云、七牛云短信。

### 平台支持

| 平台                 | 支持   | 已测试 |
| -------------------- |--------|--------|
| 测试渠道             | √      | √ |
| 阿里云               | √      | √ |
| 腾讯云               | √      | 待测试 |
| 百度云               | √      | 待测试 |
| 京东云               | √      | 待测试 |
| 七牛云               | √      | 待测试 |


### 模块说明

```lua
sms-sender
├── sms-sender-core -- 核心代码
	 └── ali -- 阿里云
	 └── tencent -- 腾讯云
└── sms-sender-boot-starter -- 整合springboot代码
└── sms-sender-jdbc-spring-boot-starter -- 整合springboot代码，增加了jdbc存储方案，自动创建表短信模板(sms_template)、短信发送记录(sms_record)2张表
	 └── processor -- 阿里云
	 	 └── SqlSendPostProcessor -- jdbc存储实现
└── sms-sender-springboot-demo -- 在springboot中使用sms-sender的demo代码
```

### 使用说明

#### 1：编译源码
mvn install，使用maven将源码编译成jar包并且安装到本地仓库，如有私服也可以部署到私服

#### 2：jar包引用（如使用阿里云），其他可参考sms-sender-core的pom配置

```
带jdbc存储方案：
<dependency>
    <groupId>com.jqdi</groupId>
    <artifactId>sms-sender-jdbc-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
普通的方案：
<dependency>
    <groupId>com.jqdi</groupId>
    <artifactId>sms-sender-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
	<groupId>com.aliyun</groupId>
	<artifactId>aliyun-java-sdk-dysmsapi</artifactId>
	<version>2.2.1</version>
</dependency>
<dependency>
	<groupId>com.aliyun</groupId>
	<artifactId>aliyun-java-sdk-core</artifactId>
	<version>4.6.3</version>
</dependency>
```
#### 3：springboot yml 配置（如使用阿里云），其他可参考sms-sender-springboot-demo的pom配置
```
smssender:
  active: ali
  ali:
    regionId: cn-hangzhou
    accessKey: LTAI5tRK8vFDnEijSRxNyDEs
    secretKey: ZIIW2Gt71FU8l48JyCIe4gzXmMYESe
    signName: XXX公司1
```
#### 4：在代码中使用
```
	@Autowired
	private SmsSender smsSender;

	public String send(String mobile, String templateCode, String templateParamJson) {
		smsSender.send(mobile, templateCode, templateParamJson);
		return "success";
	}
```

### 开源共建

#### 开源协议

sms-sender 开源软件遵循 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0.html)。
允许商业使用，但务必保留类作者、Copyright 信息。

#### 其他说明

1. 联系作者 <a href="mailto:897665787@qq.com">897665787@qq.com</a>
