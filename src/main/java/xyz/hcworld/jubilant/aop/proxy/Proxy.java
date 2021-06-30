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

import net.sf.cglib.proxy.Enhancer;
import java.lang.reflect.Method;

import java.util.Arrays;


/**
 * @ClassName: Proxy
 * @Author: 张红尘
 * @Date: 2021-06-26
 * @Version： 1.0
 */
public class Proxy {

    public static Object getProxyInstance(Object bean,Object aop) {

        //1.创建cglib的核心对象
        Enhancer enhancer = new Enhancer();
        //2.设置父类
        enhancer.setSuperclass(bean.getClass());
        //3.设置回调：（类似于InvocationHandler对象）
//        enhancer.setCallback(new net.sf.cglib.proxy.InvocationHandler() {
//            @Override
//            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//                System.out.println(bean+":前置代理逻辑");
//                Object obj = method.invoke(bean, objects);
////                System.out.println(bean+":后置代理逻辑");
//                return obj;
//            }
//        });
        ProxyInvocationHandler handler = new ProxyInvocationHandler();
        handler.setObject(bean);
        handler.setAop(aop);
        enhancer.setCallback(handler);
        Object t = enhancer.create();
        return t;



    }




}
