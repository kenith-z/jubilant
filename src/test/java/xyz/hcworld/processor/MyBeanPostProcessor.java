package xyz.hcworld.processor;

import net.sf.cglib.proxy.Enhancer;
import xyz.hcworld.jubilant.beans.BeanPostProcessor;
import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 初始化增强
 *
 * @ClassName: MyBeanPostProcessor
 * @Author: 张红尘
 * @Date: 2021-06-17
 * @Version： 1.0
 */
//@Component()
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if ("userService".equals(beanName)) {
            //1.创建cglib的核心对象
            Enhancer enhancer = new Enhancer();
            //2.设置父类
            enhancer.setSuperclass(bean.getClass());
            //3.设置回调：（类似于InvocationHandler对象）
            enhancer.setCallback(new net.sf.cglib.proxy.InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    System.out.println("前置代理逻辑");
                    Object obj = method.invoke(bean, objects);
                    System.out.println("后置代理逻辑");
                    return obj;
                }
            });
            Object t = enhancer.create();
            System.out.println(t.getClass().getSuperclass().getName());
            return t;
        }
//        //4.创建代理对象
//        return t;
//        if (beanName.equals("userService")) {
//            System.out.printf("%s初始化前\n", beanName);
//            ((UserService) bean).setName("张红尘");
//
//        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if ("userService".equals(beanName)) {
            Object proxy = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("asfasf");
                    Object obj = method.invoke(bean, args);
                    System.out.println("asfasf");
                    return obj;
                }
            });
            return proxy;
        }

        return bean;
    }
}