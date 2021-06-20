package xyz.hcworld.service.impl;

import xyz.hcworld.service.BaseService;
import xyz.hcworld.service.UserService;
import xyz.hcworld.jubilant.annotation.Autowired;
import xyz.hcworld.jubilant.beans.BeanNameAware;
import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.service.impl.t.TsService;

/**
 * @ClassName: UserService
 * @Author: 张红尘
 * @Date: 2021-06-13
 * @Version： 1.0
 */
@Component
public class UserServiceImpl implements BeanNameAware, UserService {

    @Autowired("baseService1")
    private BaseService baseService;

    @Autowired("baseService1")
    private  BaseService baseService1;


    @Autowired
    private PostService postService;

    @Autowired
    private  PostService postService1;

    @Autowired
    private TsService tsService;

    private String name;

    private String beanName;

    @Override
    public void test(){
        System.out.println(baseService.text("baseService ")+"\t"+baseService);
        System.out.println(baseService1.text("baseService1 ")+"\t"+baseService1);
        System.out.println(postService.text("postService ")+"\t"+postService);
        System.out.println(postService1.text("postService1 ")+"\t"+postService1);
        System.out.println(tsService);


        System.out.println(beanName);
        System.out.println("name:"+name);
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;

    }
}
