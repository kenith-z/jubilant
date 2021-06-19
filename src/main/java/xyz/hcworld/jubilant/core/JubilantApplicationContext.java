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
package xyz.hcworld.jubilant.core;

import xyz.hcworld.jubilant.beans.BeanDefinition;
import xyz.hcworld.jubilant.beans.BeanNameAware;
import xyz.hcworld.jubilant.beans.BeanPostProcessor;
import xyz.hcworld.jubilant.beans.InitializingBean;
import xyz.hcworld.jubilant.annotation.Autowired;
import xyz.hcworld.jubilant.annotation.Component;
import xyz.hcworld.jubilant.annotation.ComponentScan;
import xyz.hcworld.jubilant.annotation.Scope;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器类
 *
 * @ClassName: WinterApplicationContext
 * @Author: 张红尘
 * @Date: 2021-06-13
 * @Version： 1.0
 */
public class JubilantApplicationContext {
    /**
     * 配置文件
     */
    private Class configClass;

    /**
     * 单例池，保存单例对象
     */
    private final ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    /**
     * 所有Bean的配置数据的配置池
     */
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    /**
     * 初始化bean的类
     */
    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    /**
     * 构造方法
     *
     * @param configClass 配置类
     */
    public JubilantApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        // 判断是否有ComponentScan注解，如果没有就结束不往下走
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            return;
        }
        // 解析配置类
        // 对ComponentScan注解解析 -->扫描路径 -->扫描-->生成BeanDefinition对象-->存入BeanDefinitionMap
        scan(configClass);
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            // 创建所有的单例bean
            if ("singleton".equals(entry.getValue().getScope())) {
                Object bean = createBean(entry.getKey(), entry.getValue());
                singletonObjects.put(entry.getKey(), bean);
            }
        }

    }

    /**
     * 扫描
     *
     * @param configClass
     */
    private void scan(Class<?> configClass) {

        ComponentScan componentScanAnnotation = configClass.getDeclaredAnnotation(ComponentScan.class);
        // 扫描路径(不填路径默认获取Application文件所在的包) xyz.hcworld
        String path = componentScanAnnotation.value().isEmpty() ? configClass.getPackage().getName() : componentScanAnnotation.value();
        Search search = new Search();
        //获取文件夹或者jar下的所有类名
        List<String> classNames = search.getClassName(path);
        if (classNames == null) {
            return;
        }
        ClassLoader classLoader = JubilantApplicationContext.class.getClassLoader();
        try {
            for (String className : classNames) {
                Class<?> clazz = classLoader.loadClass(className);
                // 如果没有Component代表这不是一个bean结束本次循环
                if (!clazz.isAnnotationPresent(Component.class)) {
                    continue;
                }
                // 创建bean对象
                // 解析类，判断当前Bean是单例还是原型（prototype）Bean 生成BeanDefinition对象

                // 统一处理方式：BeanDefinition
                Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                // 获取Bean的名字
                String beanName = componentAnnotation.value();
                //如果没有自定义beanName则以类名（首字母小写）为beanName
                if (beanName.isEmpty()) {
                    String[] name = className.split("\\.");
                    StringBuilder nameBuffer = new StringBuilder(name[name.length - 1]);
                    beanName = nameBuffer.replace(0, 1, Character.toString(nameBuffer.charAt(0)).toLowerCase()).toString();
                }

                // bean的配置信息
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setClazz(clazz);
                // 判断Scope是否存在,存在则是自定义配置，不存在则为单例
                beanDefinition.setScope(
                        clazz.isAnnotationPresent(Scope.class) ?
                                clazz.getDeclaredAnnotation(Scope.class).value() : "singleton");
                // 存进配置池
                beanDefinitionMap.put(beanName, beanDefinition);
                // 将实现BeanPostProcessor（初始化bean）接口的类存到初始化配置池中
                if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                    BeanPostProcessor beanPostProcessor = (BeanPostProcessor) createBean(beanName, beanDefinitionMap.get(beanName));
                    beanPostProcessorList.add(beanPostProcessor);
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据配置创建出一个bean对象（反射）
     *
     * @param beanDefinition 自定义配置
     * @return bean对象
     */
    public Object createBean(String beanName, BeanDefinition beanDefinition) {

        Class<?> clazz = beanDefinition.getClazz();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            // 依赖注入
            for (Field declaredField : clazz.getDeclaredFields()) {
                // 没有Autowired就不注入
                if (!declaredField.isAnnotationPresent(Autowired.class)) {
                    continue;
                }
                String[] name = declaredField.getType().getName().split("\\.");
                StringBuilder nameBuffer = new StringBuilder(name[name.length - 1]);
                nameBuffer.replace(0, 1, Character.toString(nameBuffer.charAt(0)).toLowerCase());
                // 通过反射注入属性
                Object bean = getBean(nameBuffer.toString());
                if (bean == null) {
                    throw new NullPointerException();
                }
                // 当变量为private时需要忽略访问修饰符的检查
                declaredField.setAccessible(true);
                declaredField.set(instance, bean);
            }
            // Aware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            //初始化前的增强
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }
            // BeanPostProcessor 外部扩展机制（Bean的前后置处理）
            //初始化后的增强
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }

            return instance;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取Bean对象
     *
     * @param beanName bean名字
     * @return bean对象
     */
    public Object getBean(String beanName) {
        // 判断bean是否存在,不存在抛出npe异常
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException();
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        // 判断是否是单例,是返回单例对象，不是创建原型对象
        return "singleton".equals(beanDefinition.getScope()) ?
                singletonObjects.get(beanName) : createBean(beanName, beanDefinition);
    }

    /**
     * 获取文件制定包下的所有class文件（不管有多少层）
     *
     * @param file      目录
     * @param filePaths 临时存储
     * @return 所有class文件的绝对路径
     */
    private Set<File> getFilePath(File file, Set<File> filePaths) {
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return new HashSet<>();
        }
        for (File file1 : files) {
            if (file1.isDirectory()) {
                //递归调用
                getFilePath(file1, filePaths);
                continue;
            }
            //保存文件路径到集合中
            filePaths.add(file1);
        }
        return filePaths;
    }


}
