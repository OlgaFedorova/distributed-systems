package ofedorova.logs;

import io.opentracing.Span;
import io.opentracing.contrib.web.servlet.filter.ServletFilterSpanDecorator;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerServletFilterSpanDecorator implements ServletFilterSpanDecorator {

    private final String serviceName;

    public LoggerServletFilterSpanDecorator(@Value("${spring.application.name}") String serviceName) {
        this.serviceName = serviceName;
    }


    @Override
    public void onRequest(HttpServletRequest httpServletRequest, Span span) {
        MDC.put("opentracing.traceId", span.context().toTraceId());
        MDC.put("opentracing.spanId", span.context().toSpanId());
        MDC.put("opentracing.serviceName", serviceName);
    }

    @Override
    public void onResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Span span) {

    }

    @Override
    public void onError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Throwable exception, Span span) {

    }

    @Override
    public void onTimeout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, long timeout, Span span) {

    }
}
