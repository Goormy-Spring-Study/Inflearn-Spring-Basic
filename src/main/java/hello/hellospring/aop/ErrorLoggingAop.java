package hello.hellospring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
//@Component
public class ErrorLoggingAop {
//    @Pointcut("execution(* hello.hellospring.service.*(..))")
//    private void service(){}

    @Pointcut("execution(* hello.hellospring..*(..))")
    private void all(){}

    @AfterThrowing(pointcut = "all()", throwing = "e")
    public void logAfterThrowingException(JoinPoint joinPoint, Exception e){
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String exceptionMessage = e.getMessage();

        log.error("***{}.{}()에서 에러 발생: {}***",
                className, methodName, exceptionMessage);
    }
}
