package xyz.hcworld;

import xyz.hcworld.jubilant.annotation.Autowired;
import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.service.UserService;
import xyz.hcworld.jubilant.annotation.ComponentScan;
import xyz.hcworld.jubilant.core.JubilantApplicationContext;
import xyz.hcworld.service.impl.PostService;


/**
 * @ClassName: Test
 * @Author: 张红尘
 * @Date: 2021-06-13
 * @Version： 1.0
 */
//@ComponentScan
@ComponentScan("xyz.hcworld")
public class TestApplication {

    public static void main(String[] args) {
        JubilantApplicationContext applicationContext = new JubilantApplicationContext(TestApplication.class);
        // 获取已注册的组件
        System.out.println("------------已注册的组件-------------");
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("tsService"));
        System.out.println(applicationContext.getBean("postService"));
        System.out.println(applicationContext.getBean("postService"));
        System.out.println(applicationContext.getBean("baseService1"));
        System.out.println("------------已注册的组件-------------");

        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();
//
        PostService postService = (PostService) applicationContext.getBean("postService");
        System.out.println(postService.text("aaa？",1998));
    }
}
