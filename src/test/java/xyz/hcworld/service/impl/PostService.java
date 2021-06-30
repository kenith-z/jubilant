package xyz.hcworld.service.impl;

import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.jubilant.annotation.Scope;

/**
 * @ClassName: PostService
 * @Author: 张红尘
 * @Date: 2021-06-14
 * @Version： 1.0
 */
@Component("postService")
@Scope("prototype")
public class PostService {

    public String text(String str,int a){
        return str+"10010";
    }

}
