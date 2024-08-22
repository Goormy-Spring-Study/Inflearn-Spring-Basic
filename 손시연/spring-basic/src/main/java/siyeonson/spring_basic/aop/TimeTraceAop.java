package siyeonson.spring_basic.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class TimeTraceAop {

    @Around("execution(* siyeonson.spring_basic..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        }
        finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }

}
