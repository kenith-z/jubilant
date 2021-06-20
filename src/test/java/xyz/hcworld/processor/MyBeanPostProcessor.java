package xyz.hcworld.processor;

import xyz.hcworld.service.UserService;
import xyz.hcworld.jubilant.beans.BeanPostProcessor;
import xyz.hcworld.jubilant.annotation.Component;

/**
 * 初始化增强
 *
 * @ClassName: MyBeanPostProcessor
 * @Author: 张红尘
 * @Date: 2021-06-17
 * @Version： 1.0
 */
@Component()
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (beanName.equals("userService")) {
            System.out.printf("%s初始化前\n", beanName);
            ((UserService) bean).setName("张红尘");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (beanName.equals("userService")) {
            System.out.printf("%s初始化后\n", beanName);



        }

        return bean;
    }
}
