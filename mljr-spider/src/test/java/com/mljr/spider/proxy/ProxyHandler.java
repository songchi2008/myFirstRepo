/**
 * ProxyHandler.java by Ckex.zha
 */
package com.mljr.spider.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Ckex.zha <br/>
 *         Nov 16, 20164:54:30 PM
 */
public class ProxyHandler implements InvocationHandler {

  private Object proxied;

  public ProxyHandler(Object proxied) {
    this.proxied = proxied;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    return method.invoke(proxied, args);
  }

}
