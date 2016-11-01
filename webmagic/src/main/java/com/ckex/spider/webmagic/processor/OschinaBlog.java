/**
 * OschinaBlog.java by Ckex.zha
 */
package com.ckex.spider.webmagic.processor;

import java.util.List;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author Ckex.zha <br/>
 *         Oct 31, 20161:50:33 PM
 */
@TargetUrl("http://my.oschina.net/flashsword/blog/\\d+")
public class OschinaBlog {

  public OschinaBlog() {
    super();
  }

  @ExtractBy("//title")
  private String title;

  @ExtractBy(value = "div.BlogContent", type = ExtractBy.Type.Css)
  private String content;

  @ExtractBy(value = "//div[@class='BlogTags']/a/text()")
  private List<String> tags;
}
