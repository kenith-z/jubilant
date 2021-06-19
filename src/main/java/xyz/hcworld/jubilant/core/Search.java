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

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @ClassName: Search
 * @Author: 张红尘
 * @Date: 2021-06-18
 * @Version： 1.0
 */
public class Search {


    /**
     * 获取某包下（包括该包的所有子包）所有类
     *
     * @param packageName 包名
     * @return 类的完整名称
     */
    List<String> getClassName(String packageName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            return new ArrayList<>();
        }
        return "file".equals(url.getProtocol())?
                getClassNameByFile(url.getPath(), null, packageName.replace(".", System.getProperty("file.separator")))
                :getClassNameByJar(url.getPath());
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath  文件路径
     * @param className 类名集合
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, List<String> className, String packageName) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            return myClassName;
        }
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, packageName));
                continue;
            }
            String childFilePath = childFile.getPath();
            if (childFilePath.endsWith(".class")) {
                //截取路径
                childFilePath = childFilePath.substring(childFilePath.indexOf(packageName), childFilePath.lastIndexOf("."));
                //将反斜杠转成.
                myClassName.add(childFilePath.replace(System.getProperty("file.separator"), "."));
            }
        }
        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath jar文件路径
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath) {
        List<String> myClassName = new ArrayList<>();
        try (JarFile jarFile = new JarFile(System.getProperty("user.dir") + System.getProperty("file.separator") + System.getProperty("java.class.path"))) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.startsWith(jarPath) && entryName.endsWith(".class")) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myClassName;
    }
}