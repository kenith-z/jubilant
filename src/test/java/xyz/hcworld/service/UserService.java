package xyz.hcworld.service;

import xyz.hcworld.service.impl.PostService;
import xyz.hcworld.jubilant.annotation.Autowired;
import xyz.hcworld.jubilant.beans.BeanNameAware;
import xyz.hcworld.jubilant.annotation.Component;

/**
 * @ClassName: UserService
 * @Author: 张红尘
 * @Date: 2021-06-13
 * @Version： 1.0
 */
@Component
public class UserService implements BeanNameAware {

    @Autowired
    private  BaseService baseService;

    @Autowired
    private  BaseService baseService1;


    @Autowired
    private PostService postService;

    @Autowired
    private  PostService postService1;

    private String name;

    private String beanName;

    public void test(){
        System.out.println(baseService.text("baseService ")+"\t"+baseService);
        System.out.println(baseService1.text("baseService1 ")+"\t"+baseService1);
        System.out.println(postService.text("postService ")+"\t"+postService);
        System.out.println(postService1.text("postService1 ")+"\t"+postService1);
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
