package org.eple0329.springbasic.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class TimeTraceAop {

        // 전체에서 실행
        @Pointcut("execution(* org.eple0329..*.*(..))")
        public void all() {
        }

        // Controller 에서만 실행
        @Pointcut("execution(* org.eple0329..*Controller.*(..))")
        public void controller() {
        }

        // 각 Class에서의 실행 시간을 측정
        @Around("all()")
        public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
                log.info("기능 시작!!!!!!!");
                // 시작할 때 시간을 찍음
                long start = System.currentTimeMillis();
                try {
                        // 작업을 처리함
                        return joinPoint.proceed();
                } finally {
                        // 작업을 마치고 시간을 찍음
                        long end = System.currentTimeMillis();
                        long timeinMs = end - start;
                        // 해당
                        log.info("{},  {}ms", joinPoint.getSignature().toShortString(),
                                timeinMs);
                }
        }


        // Controller 에서의 요청과 응답을 로깅
        @Around("controller()")
        public Object loggingBefore(ProceedingJoinPoint joinPoint) throws Throwable {
                // 요청이 들어왔을 때 요청 값을 가져오기 위함
                HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
                        RequestContextHolder.getRequestAttributes())).getRequest();

                String controller = joinPoint.getSignature().getDeclaringType().getName();
                String method = joinPoint.getSignature().getName();

                try {
                        String decodedURI = URLDecoder.decode(request.getRequestURI(), "UTF-8");
                        String slicedController = controller.replace(
                                "org.eple0329.springbasic.controller.", "");
                        // [POST] /members/new
                        log.info("[{}] {}", request.getMethod(), decodedURI);
                        // method: MemberController.create
                        log.info("요청 경로: {}.{}", slicedController, method);
                        log.info("파라미터: {}", getParams(request));
                } catch (Exception e) {
                        log.error("로깅 에러 발생", e);
                }

                // 요청을 수행하고 나온 응답값을 반환
                Object result = joinPoint.proceed();
                // 응답값에 로그를 찍음
                log.info("요청 응답: {}", result);

                return result;
        }

        private static JSONObject getParams(HttpServletRequest request) {
                JSONObject jsonObject = new JSONObject();
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                        String param = params.nextElement();
                        String replaceParam = param.replaceAll("\\.", "-");
                        jsonObject.put(replaceParam, request.getParameter(param));
                }
                return jsonObject;
        }

}