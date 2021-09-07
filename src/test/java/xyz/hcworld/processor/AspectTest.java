package xyz.hcworld.processor;


import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.jubilant.aop.*;
import xyz.hcworld.jubilant.aop.proxy.MyBeanPostProcessor;

import java.lang.reflect.Method;
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
public class AspectTest extends MyBeanPostProcessor {

    @Override
    public void before(Method method, Object[] params)  {
        System.out.println("前置增强1");
        System.out.println(method.toString());
        System.out.println(Arrays.asList(params));
    }

    @Override
    public void after(Method method, Object[] params) {

    }

    @Override
    public void end(Method method, Object[] params) {

    }
}
