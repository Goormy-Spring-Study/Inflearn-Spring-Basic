package hello.hellospring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ErrorLoggingAndContinueAop {
    @Pointcut("execution(* hello.hellospring..*(..))")
    private void all(){}

    @Pointcut("execution(* *..*service.*(..))")
    private void service() {}

    @Around("all()")
    public Object logAndContinue(ProceedingJoinPoint joinPoint) throws Throwable{
        String signature = joinPoint.getSignature().toShortString();
        try {
            // 트랜잭션 로그
            log.info("[트랜잭션 시작] {}", signature);
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", signature);
            return result;
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("***{}에서 에러 발생: {}***",
                    signature, exceptionMessage);
            // 에러 발생 시 커밋하지 않고 롤백
            log.info("[트랜잭션 롤백] {}", signature);
            return null;
        }
    }
}
