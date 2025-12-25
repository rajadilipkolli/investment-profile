/* Licensed under Apache-2.0 2025 */
package com.zakura.portfolioservice.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class LogTimeAndMethodInvocationAspect {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Around("@annotation(LogProcessTime)")
    public Object logMethods(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();

        long startTime = new Date().getTime();
        Object response = pjp.proceed(pjp.getArgs());
        long endTime = new Date().getTime();

        log.info("{}.{} Execution time: {} ms", className, methodName, (endTime - startTime));
        return response;
    }

    @Around("@annotation(LogMethodInvocation)")
    public Object logMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();

        log.info("-> method {}.{} invocation", className, methodName);

        Object response = pjp.proceed(pjp.getArgs());

        log.info("-> method {}.{} execution complete", className, methodName);
        return response;
    }

    @Around("@annotation(LogMethodInvocationAndParams)")
    public Object logMethodInvocationAndParams(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();

        String[] argsNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Object[] values = pjp.getArgs();

        Map<String, Object> params = new HashMap<>();
        if (argsNames.length != 0) {
            for (int i = 0; i < argsNames.length; i++) {
                params.put(argsNames[i], values[i]);
            }
        }

        log.info("-> method {}.{} invocation", className, methodName);
        if (!params.isEmpty() && log.isDebugEnabled()) {
            log.debug("{}", gson.toJson(params));
        }

        Object response = pjp.proceed(pjp.getArgs());

        log.info("-> method {}.{} execution complete", className, methodName);
        return response;
    }
}
