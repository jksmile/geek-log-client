# geek-log-client
标准化日志组件
ek-log-client</artifactId>
<version>1.0.0</version>
</dependency>

```

### 配置文件

1. 在 `src/main/resources/META-INF/cfg/` 目录下创建 `logger.cfg` 文件，配置日志系统组件
2. 在 `src/main/resources/META-INF/log/` 目录下创建 `*.log.xml` 文件，定义日志消息
3. 在 `src/main/resources/META-INF/error/` 目录下创建 `*.error.xml` 文件，定义错误消息

## 使用示例

### 获取 FLogger 实例

```java
import com.geek.pf.log.FLogger;
import com.geek.pf.log.FLoggerFactory;

public class MyClass {
    private static final FLogger logger = FLoggerFactory.getLogger(MyClass.class);
    
    public void doSomething() {
        // 使用消息 ID 记录日志
        logger.log("MSG_001", "param1", "param2");
        
        // 使用对象记录日志
        logger.log(new MyLogObject());
        
        // 使用 SLF4J 方法记录日志
        logger.info("Info message");
        logger.error("Error message", new Exception("Something went wrong"));
    }
}
```

### 定义日志消息

在 `src/main/resources/META-INF/log/app.log.xml` 文件中：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<messages>
    <message id="MSG_001" level="INFO">
        This is a message with parameters: {0}, {1}
    </message>
    <message id="MSG_002" level="ERROR" alarm="true">
        This is an error message that triggers an alarm
    </message>
</messages>
```

### 定义错误消息

在 `src/main/resources/META-INF/error/app.error.xml` 文件中：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<messages>
    <message id="ERR_001" level="ERROR">
        Database connection failed: {0}
    </message>
    <message id="ERR_002" level="WARN">
        Configuration property {0} is missing, using default value: {1}
    </message>
</messages>
```

## 配置指南

### logger.cfg 配置

在 `src/main/resources/META-INF/cfg/logger.cfg` 文件中配置日志系统组件：

```properties
# 消息加载器
messageLoader=com.geek.pf.log.loader.local.ResourceMessageLoader

# 消息验证器
messageValidator=com.geek.pf.log.validator.DefaultMessageValidator

# 日志转换器
logConverter=com.geek.pf.log.converter.DefaultLogConverter

# 告警处理器
monitorAlarm=com.geek.pf.log.alarm.DefaultMonitorAlarm

# 日志消息路径模式
logMessagePattern=classpath*:META-INF/log/*.log.xml

# 错误消息路径模式
errorMessagePattern=classpath*:META-INF/error/*.error.xml
```

### 日志级别

支持的日志级别：

- TRACE
- DEBUG
- INFO
- WARN
- ERROR

## 扩展机制

Geek Log Client 提供了多个扩展点，可以通过实现相应的接口来自定义行为：

### 自定义消息加载器

实现 `IMessageLoader` 接口，并在 `logger.cfg` 中配置：

```java
public class CustomMessageLoader implements IMessageLoader {
    @Override
    public List<Message> load() {
        // 自定义加载逻辑
        return messages;
    }
}
```

### 自定义消息验证器

实现 `IMessageValidator` 接口，并在 `logger.cfg` 中配置：

```java
public class CustomMessageValidator implements IMessageValidator {
    @Override
    public void validate(List<Message> messages) {
        // 自定义验证逻辑
    }
}
```

### 自定义日志转换器

实现 `ILogConverter` 接口，并在 `logger.cfg` 中配置：

```java
public class CustomLogConverter implements ILogConverter {
    @Override
    public FormatLogMessage convert(String messageId, Object... args) {
        // 自定义转换逻辑
        return formatLogMessage;
    }
    
    @Override
    public FormatLogMessage convert(Object obj) {
        // 自定义转换逻辑
        return formatLogMessage;
    }
}
```

### 自定义告警处理器

实现 `IMonitorAlarm` 接口，并在 `logger.cfg` 中配置：

```java
public class CustomMonitorAlarm implements IMonitorAlarm {
    @Override
    public void alarm(FormatLogMessage message) {
        // 自定义告警逻辑
    }
}
```

## 注意事项

1. 确保所有消息 ID 唯一
2. 配置文件路径必须正确
3. 日志级别应与 Logback 配置一致
4. 告警消息应谨慎使用，避免过多告警

## 许可证

[Apache License 2.0](LICENSE)
