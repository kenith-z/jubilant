package xyz.hcworld.service.impl;

import xyz.hcworld.jubilant.annotation.Autowired;
import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.jubilant.beans.InitializingBean;
import xyz.hcworld.service.BaseService;

/**
 * @ClassName: BaseService
 * @Author: 张红尘
 * @Date: 2021-06-14
 * @Version： 1.0
 */
@Component("baseService1")
public class BaseServiceImpl implements InitializingBean, BaseService {

    @Autowired
    PostService postService;

    public String text(String str){
        return str+"10086"+"\tbaseService:"+postService.text("9");
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
