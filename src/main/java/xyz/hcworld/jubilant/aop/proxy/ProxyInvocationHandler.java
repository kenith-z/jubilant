/*
Copyright (c) [2021] [Kenith-Zhang]
[Software Name] is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details.
 */
package xyz.hcworld.jubilant.aop.proxy;

import net.sf.cglib.proxy.InvocationHandler;
import xyz.hcworld.jubilant.aop.*;
import xyz.hcworld.jubilant.beans.InitializingBean;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ProxyInvocationHandler
 * @Author: 张红尘
 * @Date: 2021-06-26
 * @Version： 1.0
 */
public class ProxyInvocationHandler implements InvocationHandler {
    /**
     * 被代理类
     */
    private Object object;

    /**
     * 切面方法
     */
    private Object aop;

    /**
     * 增强的方法
     */
    private Object invoke;

    /**
     *
     */
    private Map<String, Object> objectMethods;

    public ProxyInvocationHandler() {
        objectMethods = new HashMap<>();
        for (Method objMethod : Object.class.getMethods()) {
            objectMethods.put(objMethod.getName(), null);
        }
        for (Method initMethod : InitializingBean.class.getMethods()) {
            objectMethods.put(initMethod.getName(), null);
        }

    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setAop(Object aop) {
        this.aop = aop;
    }

    /**
     * 动态代理：实现了环绕通知、前置通知、后置通知等通知。
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法为Object自带的方法直接返回
        if (objectMethods.containsKey(method.getName())) {
            return method.invoke(object, args);
        }

        for (Method declaredMethod : aop.getClass().getDeclaredMethods()) {
            try {
                // 切点
                if (null != declaredMethod.getAnnotation(Pointcut.class)) {
                    invoke = declaredMethod.invoke(aop, args);
                }

                // 环绕增强
                if (null != declaredMethod.getAnnotation(Around.class)) {
                    aroundInform(declaredMethod, method, args);
                }
                // 前置增强
                if (null != declaredMethod.getAnnotation(Before.class)) {
                    invoke = declaredMethod.invoke(aop, args);
                }

                // 通过放射，真正执行被代理对象的方法：
                invoke = method.invoke(object, args);

                // 后置增强
                if (null != declaredMethod.getAnnotation(After.class)) {
                    invoke = declaredMethod.invoke(aop, args);
                }
                // 结果增强
                if (null != declaredMethod.getAnnotation(AfterReturning.class)) {
                    invoke = declaredMethod.invoke(aop, args);
                }

            } catch (Exception e) {
                //异常增强
                if (null != declaredMethod.getAnnotation(AfterThrowing.class)) {
                    invoke = declaredMethod.invoke(aop, args);
                }
            }

        }

        return invoke;
    }


    /**
     * 环绕通知
     *
     * @param declaredMethod 被代理对象的被代理方法
     * @param method         被代理对象的接口中声明的被代理方法
     * @param args           被代理方法的声明的入参
     */
    private void aroundInform(Method declaredMethod, Method method, Object[] args) throws Exception {

            invoke = method.invoke(object, args);
    }


}