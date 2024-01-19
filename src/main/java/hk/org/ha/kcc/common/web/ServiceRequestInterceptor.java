package hk.org.ha.kcc.common.web;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import hk.org.ha.kcc.common.logging.AlsXLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class ServiceRequestInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // X-Forwarded-For / REMOTE_ADDR
    MDC.put(AlsXLogger.CLIENT_IP, getClientIp(request));
    // X-Correlation-Id
    if (!(MDC.get(AlsXLogger.CORRELATION_ID) instanceof String)) {
      MDC.put(AlsXLogger.CORRELATION_ID, getCorrelationId(request));
    }
    // X-Transaction-Id
    MDC.put(AlsXLogger.TRANSACTION_ID, getTransactionId(request));
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, @Nullable Exception ex) throws Exception {
    MDC.remove(AlsXLogger.CLIENT_IP);
    MDC.remove(AlsXLogger.CORRELATION_ID);
    MDC.remove(AlsXLogger.TRANSACTION_ID);
  }

  private String getClientIp(HttpServletRequest request) {
    String ipList = request.getHeader(HttpHeaders.FORWARDED_FOR);
    if (StringUtils.isBlank(ipList) || ipList.equalsIgnoreCase("unknown")) {
      return request.getRemoteAddr();
    }
    return ipList.split(",")[0];
  }

  private String getCorrelationId(HttpServletRequest request) {
    String correlationId = request.getHeader(HttpHeaders.CORRELATION_ID);
    if (StringUtils.isBlank(correlationId)) {
      return null;
    }
    return correlationId;
  }

  private String getTransactionId(HttpServletRequest request) {
    String transactionId = request.getHeader(HttpHeaders.TRANSACTION_ID);
    if (StringUtils.isBlank(transactionId)) {
      return UUID.randomUUID().toString();
    }
    return transactionId;
  }
}
