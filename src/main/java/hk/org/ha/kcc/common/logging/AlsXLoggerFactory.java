package hk.org.ha.kcc.common.logging;

import org.slf4j.LoggerFactory;

public class AlsXLoggerFactory {

  public static AlsXLogger getXLogger(String name) {
    return new AlsXLogger(LoggerFactory.getLogger(name));
  }

  public static AlsXLogger getXLogger(Class<?> clazz) {
    return getXLogger(clazz.getName());
  }
}
