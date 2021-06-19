package xyz.hcworld.service;

import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.jubilant.beans.InitializingBean;

/**
 * @ClassName: BaseService
 * @Author: 张红尘
 * @Date: 2021-06-14
 * @Version： 1.0
 */
@Component("baseService")
public class BaseService implements InitializingBean {

    public String text(String str){
        return str+"10086";
    }

    /**
     * 应用层级别框架提供的初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BaseService初始化");
    }
}
