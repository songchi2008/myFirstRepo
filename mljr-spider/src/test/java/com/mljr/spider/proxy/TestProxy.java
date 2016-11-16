/**
 * TestProxy.java by Ckex.zha
 */
package com.mljr.spider.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import sun.misc.ProxyGenerator;

import java.io.*;

/**
 * @author Ckex.zha <br/>
 *         Nov 16, 20164:50:23 PM
 */
public class TestProxy {
  public static void main(String args[]) throws InterruptedException {
    Subject proxySubject =
        (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[] {Subject.class}, new ProxyHandler(new RealSubject()));

    proxySubject.doSomething();
    System.out.println(proxySubject.toString());

    proxySubject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[] {Subject.class}, new ProxyHandler(new RealSubject()));

    proxySubject.doSomething();
    System.out.println(proxySubject.toString());
    // write proxySubject class binary data to file
    // createProxyClassFile();
    TimeUnit.SECONDS.sleep(1000);
  }

  public static void createProxyClassFile() {
    String name = "ProxySubject";
    byte[] data = ProxyGenerator.generateProxyClass(name, new Class[] {Subject.class});
    try {
      FileOutputStream out = new FileOutputStream(name + ".class");
      out.write(data);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
