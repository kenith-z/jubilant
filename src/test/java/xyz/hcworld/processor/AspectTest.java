package xyz.hcworld.processor;


import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.jubilant.aop.*;

import java.util.Arrays;
import java.util.StringJoiner;


/**
 * 日志AOP处理
 *
 * @ClassName: LogAspect
 * @Author: 张红尘
 * @Date: 2021-05-06
 * @Version： 1.0
 */
@Aspect
@Component
public class AspectTest {

    /**
     *  若@Pointcut("xyz.hcworld.service.impl")则拦截xyz.hcworld.service.impl的所有类
     * 若@Pointcut("xyz.hcworld.service.impl.BaseServiceImpl")则拦截xyz.hcworld.service.impl.BaseServiceImpl类
     *
     */
    @Pointcut("xyz.hcworld.service.impl")
    public void log(String str) {
        System.out.println("经过AOP的log方法（单参）");
    }
    @Pointcut("xyz.hcworld.service.impl")
    public void log() {
        System.out.println("经过AOP的log方法（无参）");
    }
    @Pointcut("xyz.hcworld.service.impl")
    public void log(String str, int a) {
        System.out.println("经过AOP的log方法（双参）");
    }

    /**
     * 前置增强
     */
    @Before()
    public void doBefore(String str, int a) {

        System.out.println("经过AOP的前置增强方法（双参）" + str + a);
    }
    @Before()
    public void doBefore() {

        System.out.println("经过AOP的前置增强方法（无参）");
    }
    /**
     * 只增强有一个参数的类
     * @param str
     */
    @Before()
    public void doBefore(String str) {

        System.out.println("经过AOP的前置增强方法（单参数）" + str);
    }
    /**
     * 后置增强
     */
    @After()
    public void doAfter() {
        System.out.println("经过AOP的后置增强方法");
    }

    /**
     * 环绕增强
     */
    @Around()
    public void doAround() {
        System.out.println("经过AOP的环绕增强方法");
    }

    /**
     * 拦截最后返回
     *
     * @param result 返回结果
     */
    @AfterReturning
    public String doAfterReturn(String result) {
        System.out.println("拦截" + result);
        result = "re";
        return result;
    }
}
