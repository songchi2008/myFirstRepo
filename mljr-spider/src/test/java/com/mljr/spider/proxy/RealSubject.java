/**
 * RealSubject.java by Ckex.zha
 */
package com.mljr.spider.proxy;

/**
 * @author Ckex.zha <br/>
 *         Nov 16, 20164:52:28 PM
 */
public class RealSubject implements Subject {

  @Override
  public void doSomething() {
    System.out.println("call doSomething()");
  }

}
