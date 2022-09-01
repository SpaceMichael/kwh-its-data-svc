package hk.org.ha.kcc.common.web;

import org.slf4j.MDC;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientRequest.Builder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import hk.org.ha.kcc.common.logging.AlsXLogger;
import reactor.core.publisher.Mono;

public interface HttpHeaders {

  public static final String FORWARDED_FOR = "X-Forwarded-For";
  public static final String CORRELATION_ID = "X-Correlation-Id";
  public static final String TRANSACTION_ID = "X-Transaction-Id";

  public static final ExchangeFilterFunction filter = ExchangeFilterFunction.ofRequestProcessor(request -> {
    Builder builder = ClientRequest.from(request);
    if ((MDC.get(AlsXLogger.CLIENT_IP) instanceof String)) {
      builder.header(FORWARDED_FOR, (String) MDC.get(AlsXLogger.CLIENT_IP));
    }
    if ((MDC.get(AlsXLogger.CORRELATION_ID) instanceof String)) {
      builder.header(CORRELATION_ID, (String) MDC.get(AlsXLogger.CORRELATION_ID));
    }
    if ((MDC.get(AlsXLogger.TRANSACTION_ID) instanceof String)) {
      builder.header(TRANSACTION_ID, (String) MDC.get(AlsXLogger.TRANSACTION_ID));
    }
    return Mono.just(builder.build());
  });
}
