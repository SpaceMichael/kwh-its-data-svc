package hk.org.ha.kcc.common.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.ext.XLogger;
import org.slf4j.helpers.MessageFormatter;

import hk.org.ha.service.app.audit.als.AlsLogger;
import hk.org.ha.service.app.audit.als.AlsMessage;

public class AlsXLogger extends XLogger {

  private static final Logger log = LoggerFactory.getLogger(AlsXLogger.class);

  public static final String CLIENT_IP = "client-ip";
  public static final String USER_ID = "user-id";
  public static final String CORRELATION_ID = "correlation-id";
  public static final String TRANSACTION_ID = "transaction-id";

  public static final String DEFAULT_PROJECT_CODE = "KCC";

  public static final String PROJECT_CODE;

  private AlsLogger alsLogger = new AlsLogger();

  static {
    String value = System.getenv("ALS_PROJECT_CODE");
    if (value != null) {
      PROJECT_CODE = value;
      log.debug("ALS_PROJECT_CODE: {}", PROJECT_CODE);
    } else {
      PROJECT_CODE = DEFAULT_PROJECT_CODE;
      log.debug("ALS_PROJECT_CODE not set, use default project code: {}", PROJECT_CODE);
    }
  }

  public AlsXLogger(Logger logger) {
    super(logger);
  }

  public AlsMessage createMessage(LogType logType, String description, String content) {
    return this.createMessage(logType, description, content, null);
  }

  public AlsMessage createMessage(LogType logType, String description, String content, String caseNo) {
    AlsMessage message = new AlsMessage();
    message.setLogType(logType.toString());
    message.setClientIp(getClientIp());
    message.setUserId(getUserId());
    message.setProjectCode(PROJECT_CODE);
    message.setCorrelationId(getCorrelationId());
    message.setTransactionId(getTransactionId());
    message.setDescription(description);
    message.setCaseNo(caseNo);
    message.setContent(content);
    try {
      FieldUtils.writeField(message, "sourceClass", this.getName(), true);
    } catch (IllegalAccessException ignore) {
    }
    return message;
  }

  public void out(LogType logType, String description, String content, String caseNo) {
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    if (description == null) {
      description = element.getMethodName();
    }
    AlsMessage message = this.createMessage(logType, description, content, caseNo);
    message.setFunctionId(getFunctionId(element));
    this.out(message);
  }

  public void out(AlsMessage message) {
    switch (LogType.valueOf(message.getLogType())) {
      case CRITICAL:
        super.error(message.getContent());
        break;
      case WARN:
        super.warn(message.getContent());
        break;
      case AUDIT:
      case INFO:
        super.info(message.getContent());
        break;
      case DEBUG:
        super.debug(message.getContent());
        break;
      case TRACE:
      default:
        super.trace(message.getContent());
    }
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    if (message.getDescription() == null) {
      message.setDescription(element.getMethodName());
    }
    if (message.getFunctionId() == null) {
      message.setFunctionId(getFunctionId(element));
    }
    this.alsLogger.out(message);
  }

  private void out(LogType logType, StackTraceElement element, String format, Object... argArray) {
    String description = element.getMethodName();
    String content = MessageFormatter.arrayFormat(format, argArray).getMessage();
    AlsMessage message = this.createMessage(logType, description, content);
    message.setFunctionId(getFunctionId(element));
    this.alsLogger.out(message);
  }

  private void out(LogType logType, StackTraceElement element, String msg, Throwable t) {
    String description = msg;
    String content = getStackTrace(t);
    AlsMessage message = this.createMessage(logType, description, content);
    message.setFunctionId(getFunctionId(element));
    this.alsLogger.out(message);
  }

  @Override
  public void trace(String msg) {
    super.trace(msg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.TRACE, element, msg, new Object[] {});
  }

  @Override
  public void trace(String format, Object arg) {
    super.trace(format, arg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.TRACE, element, format, new Object[] { arg });
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    super.trace(format, arg1, arg2);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.TRACE, element, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void trace(String format, Object... argArray) {
    super.trace(format, argArray);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.TRACE, element, format, argArray);
  }

  @Override
  public void trace(String msg, Throwable t) {
    super.trace(msg, t);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.TRACE, element, msg, t);
  }

  @Override
  public void debug(String msg) {
    super.debug(msg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.DEBUG, element, msg, new Object[] {});
  }

  @Override
  public void debug(String format, Object arg) {
    super.debug(format, arg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.DEBUG, element, format, new Object[] { arg });
  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    super.debug(format, arg1, arg2);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.DEBUG, element, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void debug(String format, Object... argArray) {
    super.debug(format, argArray);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.DEBUG, element, format, argArray);
  }

  @Override
  public void debug(String msg, Throwable t) {
    super.debug(msg, t);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.DEBUG, element, msg, t);
  }

  @Override
  public void info(String msg) {
    super.info(msg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.INFO, element, msg, new Object[] {});
  }

  @Override
  public void info(String format, Object arg) {
    super.info(format, arg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.INFO, element, format, new Object[] { arg });
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    super.info(format, arg1, arg2);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.INFO, element, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void info(String format, Object... argArray) {
    super.info(format, argArray);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.INFO, element, format, argArray);
  }

  @Override
  public void info(String msg, Throwable t) {
    super.info(msg, t);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.INFO, element, msg, t);
  }

  @Override
  public void warn(String msg) {
    super.warn(msg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.WARN, element, msg, new Object[] {});
  }

  @Override
  public void warn(String format, Object arg) {
    super.warn(format, arg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.WARN, element, format, new Object[] { arg });
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    super.warn(format, arg1, arg2);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.WARN, element, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void warn(String format, Object... argArray) {
    super.warn(format, argArray);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.WARN, element, format, argArray);
  }

  @Override
  public void warn(String msg, Throwable t) {
    super.warn(msg, t);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.WARN, element, msg, t);
  }

  @Override
  public void error(String msg) {
    super.error(msg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.CRITICAL, element, msg, new Object[] {});
  }

  @Override
  public void error(String format, Object arg) {
    super.error(format, arg);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.CRITICAL, element, format, new Object[] { arg });
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    super.error(format, arg1, arg2);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.CRITICAL, element, format, new Object[] { arg1, arg2 });
  }

  @Override
  public void error(String format, Object... argArray) {
    super.error(format, argArray);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.CRITICAL, element, format, argArray);
  }

  @Override
  public void error(String msg, Throwable t) {
    super.error(msg, t);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.CRITICAL, element, msg, t);
  }

  @Override
  public void entry(Object... argArray) {
    super.entry(argArray);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    String messagePattern = buildEntryMessagePattern(argArray.length);
    this.out(LogType.TRACE, element, messagePattern, argArray);
  }

  @Override
  public void exit() {
    super.exit();
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.TRACE, element, "exit", new Object[] {});
  }

  @Override
  public <T> T exit(T result) {
    super.exit(result);
    StackTraceElement element = Thread.currentThread().getStackTrace()[2];
    this.out(LogType.TRACE, element, "exit with ({})", new Object[] { result });
    return result;
  }

  public static String getClientIp() {
    if (!(MDC.get(CLIENT_IP) instanceof String)) {
      return null;
    }
    return (String) MDC.get(CLIENT_IP);
  }

  public static String getUserId() {
    if (!(MDC.get(USER_ID) instanceof String)) {
      return null;
    }
    return (String) MDC.get(USER_ID);
  }

  public static String getCorrelationId() {
    if (!(MDC.get(CORRELATION_ID) instanceof String)) {
      return getTransactionId();
    }
    return (String) MDC.get(CORRELATION_ID);
  }

  public static String getTransactionId() {
    if (!(MDC.get(TRANSACTION_ID) instanceof String)) {
      MDC.put(TRANSACTION_ID, UUID.randomUUID().toString());
    }
    return (String) MDC.get(TRANSACTION_ID);
  }

  private static String getFunctionId(StackTraceElement element) {
    String fullClassName = element.getClassName();
    String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    String methodName = element.getMethodName();
    int lineNumber = element.getLineNumber();
    return className + "." + methodName + ":" + lineNumber;
  }

  private static String getStackTrace(Throwable t) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    t.printStackTrace(pw);
    sw.flush();
    return sw.toString();
  }

  private static String buildEntryMessagePattern(int len) {
    if (len > 0) {
      StringBuilder sb = new StringBuilder();
      sb.append("entry with (");
      for (int i = 0; i < len; i++) {
        sb.append("{}");
        if (i != len - 1)
          sb.append(", ");
      }
      sb.append(')');
      return sb.toString();
    } else {
      return "entry";
    }
  }
}
