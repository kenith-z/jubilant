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
package xyz.hcworld.jubilant.beans;

/**
 * 后置处理器（bean初始化前后的增强处理）
 *
 * @ClassName: BeanPostProcessor
 * @Author: 张红尘
 * @Date: 2021-06-16
 * @Version： 1.0
 */
public interface BeanPostProcessor {
    /**
     * bean初始化前调用
     *
     * @param bean     bean本身
     * @param beanName bean的名字
     * @return
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    /**
     * bean初始化后调用
     *
     * @param bean     bean本身
     * @param beanName bean的名字
     * @return
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
