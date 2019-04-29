package com.yuyue.test;

import org.jasypt.util.text.BasicTextEncryptor;

public class test {
	public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("Y1YWUJIAN");
        String url1 = textEncryptor.encrypt("jdbc:mysql://127.0.0.1:3306/yuyue?characterEncoding=UTF-8");
        String username1 = textEncryptor.encrypt("root");
        String password1 = textEncryptor.encrypt("932982514");
        System.out.println("url1:"+url1);
        System.out.println("username1:"+username1);
        System.out.println("password1:"+password1);
        String url2 = textEncryptor.encrypt("jdbc:mysql://119.3.231.11:3306/yuyue?characterEncoding=UTF-8");
        String username2 = textEncryptor.encrypt("root");
        String password2 = textEncryptor.encrypt("BackGround20151203");
        System.out.println("url2:"+url2);
        System.out.println("username2:"+username2);
        System.out.println("password2:"+password2);
    }
}
