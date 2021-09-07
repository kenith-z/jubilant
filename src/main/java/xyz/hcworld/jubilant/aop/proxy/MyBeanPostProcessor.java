package xyz.hcworld.jubilant.aop.proxy;


import net.sf.cglib.proxy.Enhancer;
import xyz.hcworld.jubilant.aop.Aspect;
import xyz.hcworld.jubilant.beans.BeanPostProcessor;
import xyz.hcworld.jubilant.annotation.Component;
import java.lang.reflect.Method;

/**
 * 初始化增强
 *
 * @ClassName: MyBeanPostProcessor
 * @Author: 张红尘
 * @Date: 2021-06-17
 * @Version： 1.0
 */

public abstract class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if ("userService".equals(beanName)) {

            //1.创建cglib的核心对象
            Enhancer enhancer = new Enhancer();
            //2.设置父类
            enhancer.setSuperclass(bean.getClass());
            //3.设置回调：（类似于InvocationHandler对象）
            enhancer.setCallback((net.sf.cglib.proxy.InvocationHandler) (o, method, objects) -> {
                before(method,objects);
                Object obj = method.invoke(bean, objects);
                after(method,objects);
                return obj;
            });
            Object t = enhancer.create();
            System.out.println(t.getClass().getSuperclass().getName());
            return t;
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if ("userService".equals(beanName)) {
            //1.创建cglib的核心对象
            Enhancer enhancer = new Enhancer();
            //2.设置父类
            enhancer.setSuperclass(bean.getClass());
            //3.设置回调：（类似于InvocationHandler对象）
            enhancer.setCallback((net.sf.cglib.proxy.InvocationHandler) (o, method, objects) -> {
                Object obj = method.invoke(bean, objects);
                end(method,objects);
                return obj;
            });
            Object t = enhancer.create();
            System.out.println(t.getClass().getSuperclass().getName());
            return t;
        }
        return bean;
    }

    /**
     * 前置增强
     */
    public abstract void before(Method method, Object[] params) throws Throwable ;

    /**
     * 后置增强
     */
    public abstract void after(Method method, Object[] params) throws Throwable ;



    /**
     * 最终增强
     */
    public abstract void end(Method method, Object[] params);



}