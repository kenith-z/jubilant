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
 * 初始化机制
 * @ClassName: Initializingbean
 * @Author: 张红尘
 * @Date: 2021-06-16
 * @Version： 1.0
 */
public interface InitializingBean {
    /**
     * 初始化
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
