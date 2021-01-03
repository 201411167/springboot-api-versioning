package me.minjun.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class LogAspect {

//    @Around("@annotation(LogExecutionTime)")
    @Around("execution(* me.minjun.demo.controller.WebRestController.addUser(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        watch.start();

        Object rtn = joinPoint.proceed(); // -> 타겟 메소드를 실행
        watch.stop();

        log.info("time measurement : " + watch.prettyPrint());

        return rtn;
    }
}
