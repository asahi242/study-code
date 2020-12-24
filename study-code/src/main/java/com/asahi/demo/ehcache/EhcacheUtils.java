package com.asahi.demo.ehcache;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class EhcacheUtils {
    private static Cache cache = null;
    private static CacheManager  cacheManager = null;
    static {
        //1.创建缓存管理器
        EhcacheUtils ehcacheUtils = new EhcacheUtils();
        File file;
        Cache cache = null;
        try {
            String s = ehcacheUtils.showURL();
            file = new File(s+"/ehcache.xml");
            if (file.exists()){
                System.out.println("true");
            }
            EhcacheUtils.cacheManager = CacheManager.create(file.toString());
            //2.获取缓存对象
            cache = cacheManager.getCache("demoCache");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EhcacheUtils.cache = cache;
    }

    public static Cache getCache(){
        return cache;
    }
    public static void flush(){
        cache.flush();
    }
    public static void shutdown(){
        EhcacheUtils.cacheManager.shutdown();
    }
    public static void setElement(String key,Object obj){
        Cache cache = EhcacheUtils.getCache();
        //3.创建元素
        Element element = new Element(key,obj);
        //4.将元素添加到缓存
        cache.put(element);
    }
    public static Element getElement(String key){
        Cache cache = EhcacheUtils.getCache();
        //5.获取缓存
        Element v1 = cache.get(key);
        return v1;
    }
    public static Object getEleObject(String key){
        Element element = EhcacheUtils.getElement(key);
        return element.getObjectValue();
    }
    public static Boolean removeElement(String key){
        Cache cache = EhcacheUtils.getCache();
        boolean remove = cache.remove(key);
        return remove;
    }
    public static void main(String[] args) throws IOException {
        EhcacheUtils.setElement("k1","v1");
        Element v1 = EhcacheUtils.getElement("k1");


        System.out.println(v1);
        System.out.println(v1.getObjectValue());
        //6.删除元素
        EhcacheUtils.removeElement("k1");
        EhcacheUtils.setElement("dog",new Dog("1","dog",12));
        Element v2 = EhcacheUtils.getElement("dog");
        Dog dog1 = (Dog) v2.getObjectValue();
        System.out.println(dog1);
        System.out.println(cache.getSize());
        //7.刷新缓存
        cache.flush();
        //8.关闭缓存管理器
        cacheManager.shutdown();
    }
    public String showURL() throws IOException {
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);
        return f.toString();
    }
    static class Dog implements Serializable {
        String id;
        String name;
        int age;
        Dog(String id,String name,int age){
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
}
