package ru.aasmc.gateway.filters;

import brave.Tracer;
import brave.propagation.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseFilter {

    final Logger log =LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    private Tracer tracer;

    /**
     * Passes the necessary headers back to the client of the microservice.
     */
    @Bean
    public GlobalFilter postGlobalFilter() {
        return ((exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                String traceId;
                TraceContext context = tracer.currentSpan().context();
                if (context != null) {
                    traceId = context.traceIdString();
                } else {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    traceId = filterUtils.getCorrelationId(requestHeaders);
                }
                log.debug("Adding the correlation id to the outbound headers. {}", traceId);
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, traceId);
                log.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
            }));
        });
    }
}
