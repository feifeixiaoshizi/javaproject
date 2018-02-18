package com.example.aspect;

import com.example.demo.ILove;
import com.example.demo.LoveImpl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 在aop中主要涉及到切点和增强，切面。
 */
@Component
@Aspect
public class TestAop {
    @DeclareParents(value = "com.example.demo.IDemo+", defaultImpl = LoveImpl.class)
    public ILove love;

    //public Map<String, String> cn.itcast.ssm.controller.getItemTypes()
    @Pointcut("execution(* com.example.demo.*.*(..))")
    public void point() {
    }

    @Before("point()")
    public void beforeSleep(JoinPoint jp) {
        System.out.println("before mehtod run ：" + jp.getSignature().getName());
    }

    @AfterReturning("point()")
    public void afterSleep(JoinPoint jp) {
        System.out.println("after method runn：" + jp.getSignature().getName());
    }


    @Around("point()")
    public Object aroundSleepReturn(ProceedingJoinPoint pjp) {
        System.out.println("before around  return mehtod run ：" + pjp.getSignature().getName());
        Object result = null;
        try {
            result =  pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("around is has a error " );
        }

        System.out.println("after around return method runn：" + pjp.getSignature().getName());
        return result;
    }

/*

    */
/*如果返回值为空，则拦截的有返回值的方法将返回为空值*//*

    @Around("point()")
    public void aroundSleep(ProceedingJoinPoint pjp) {
        System.out.println("before around void   mehtod run ：" + pjp.getSignature().getName());
        try {
            pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("around is has a error " );
        }
        System.out.println("after around void method runn：" + pjp.getSignature().getName());
    }
*/










    /*
    * 日志分析：
     *
 before around void   mehtod run ：getString
before around  return mehtod run ：getString
after around return method run： getString
after around void method run：getString
null


通过分析可以得出，多个增强同时存在的时候，先后顺序和名字有关。
aroundSleep比aroundSleepReturn在前。
aroundSlee比aroundSleep在前。
只要有一个增强返回为空，则最终结果就返回为空。
所以使用 @Around("point()")时候要注意一下两点：
  1：方法一定要写返回值。
  2：方法参数一定是ProceedingJoinPoint（proceeding:进行，行动 ，继续）





  3：在@Around和@Before("point()")以及 @AfterReturning("point()")同时出现的情况下，
  先执行@Around然后再执行@Before和@AfterReturning

日志如下：
before around  return mehtod run ：getString
before mehtod run ：getString
after around return method run：getString
after method run：getString
lijiansheng is a woner
    *
    *
    *
    *
    * */


}
